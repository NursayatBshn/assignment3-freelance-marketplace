package repository;

import exception.DatabaseOperationException;
import exception.DuplicateResourceException;
import exception.ResourceNotFoundException;
import model.Client;
import repository.interfaces.CrudRepository;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements CrudRepository<Client> {

    @Override
    public void update(int id, Client client) {
        String sql = "UPDATE clients SET first_name = ?, last_name = ?, email = ? WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Client not found with id: " + id);
            }

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateResourceException("Email " + client.getEmail() + " is already taken");
            }
            throw new DatabaseOperationException("Failed to update client", e);
        }
    }

    @Override
    public void create(Client client) {
        String sql = "INSERT INTO clients (first_name, last_name, email, registered_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setDate(4, Date.valueOf(client.getRegisteredAt()));

            ps.executeUpdate();

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateResourceException("Client with email already exists: " + client.getEmail());
            }
            throw new DatabaseOperationException("Failed to create client", e);
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT client_id, first_name, last_name, email, registered_at FROM clients";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("client_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("registered_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch clients", e);
        }
        return clients;
    }

    @Override
    public Client getById(int id) {
        String sql = "SELECT client_id, first_name, last_name, email, registered_at FROM clients WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("client_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getDate("registered_at").toLocalDate()
                    );
                }
            }
            throw new ResourceNotFoundException("Client not found with id: " + id);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch client", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Client not found with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete client", e);
        }
    }
}