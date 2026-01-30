package repository;

import exception.DatabaseOperationException;
import exception.DuplicateResourceException;
import exception.ResourceNotFoundException;
import model.Freelancer;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FreelancerRepository {

    public void create(Freelancer freelancer) {
        String sql = "INSERT INTO freelancers (first_name, last_name, email, rating, joined_at) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, freelancer.getFirstName());
            ps.setString(2, freelancer.getLastName());
            ps.setString(3, freelancer.getEmail());
            ps.setDouble(4, freelancer.getRating());
            ps.setDate(5, Date.valueOf(freelancer.getJoinedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            // Добавь эту проверку:
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateResourceException("Freelancer with email already exists: " + freelancer.getEmail());
            }
            throw new DatabaseOperationException("Failed to create freelancer", e);
        }
    }

    public List<Freelancer> getAll() {

        List<Freelancer> freelancers = new ArrayList<>();

        String sql = """
            SELECT freelancer_id, first_name, last_name, email, rating, joined_at
            FROM freelancers;
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                freelancers.add(new Freelancer(
                        rs.getInt("freelancer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDouble("rating"),
                        rs.getDate("joined_at").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch freelancers", e);
        }

        return freelancers;
    }

    public Freelancer getById(int id) {

        String sql = """
            SELECT freelancer_id, first_name, last_name, email, rating, joined_at
            FROM freelancers
            WHERE freelancer_id = ?;
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Freelancer(
                            rs.getInt("freelancer_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getDouble("rating"),
                            rs.getDate("joined_at").toLocalDate()
                    );
                }
            }

            throw new ResourceNotFoundException("Freelancer not found with id: " + id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch freelancer", e);
        }
    }

    public Freelancer findByEmail(String email) {
        String sql = """
        SELECT freelancer_id, first_name, last_name, email, rating, joined_at
        FROM freelancers
        WHERE email = ?;
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Freelancer(
                            rs.getInt("freelancer_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getDouble("rating"),
                            rs.getDate("joined_at").toLocalDate()
                    );
                }
            }
            return null; // Возвращаем null, если фрилансер не найден
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find freelancer by email", e);
        }
    }

    public void update(int id, Freelancer freelancer) {

        String sql = """
            UPDATE freelancers
            SET first_name = ?, last_name = ?, email = ?, rating = ?
            WHERE freelancer_id = ?;
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, freelancer.getFirstName());
            ps.setString(2, freelancer.getLastName());
            ps.setString(3, freelancer.getEmail());
            ps.setDouble(4, freelancer.getRating());
            ps.setInt(5, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Freelancer not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update freelancer", e);
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM freelancers WHERE freelancer_id = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Freelancer not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete freelancer", e);
        }
    }
}
