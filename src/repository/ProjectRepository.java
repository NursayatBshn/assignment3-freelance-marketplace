package repository;

import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.Client;
import model.Project;
import repository.interfaces.CrudRepository;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements CrudRepository<Project> {

    @Override
    public void create(Project project) {
        String sql = "INSERT INTO projects (client_id, title, budget, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, project.getClient().getId());
            ps.setString(2, project.getTitle());
            ps.setDouble(3, project.getBudget());
            ps.setDate(4, Date.valueOf(project.getCreatedAt()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create project", e);
        }
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.project_id, p.title, p.budget, p.created_at, " +
                "c.client_id, c.first_name, c.last_name, c.email, c.registered_at " +
                "FROM projects p JOIN clients c ON p.client_id = c.client_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("registered_at").toLocalDate()
                );

                projects.add(new Project(
                        rs.getInt("project_id"),
                        rs.getString("title"),
                        rs.getDouble("budget"),
                        rs.getDate("created_at").toLocalDate(),
                        client
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch projects", e);
        }
        return projects;
    }

    @Override
    public Project getById(int id) {
        String sql = "SELECT p.project_id, p.title, p.budget, p.created_at, " +
                "c.client_id, c.first_name, c.last_name, c.email, c.registered_at " +
                "FROM projects p JOIN clients c ON p.client_id = c.client_id " +
                "WHERE p.project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client(
                            rs.getInt("client_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getDate("registered_at").toLocalDate()
                    );

                    return new Project(
                            rs.getInt("project_id"),
                            rs.getString("title"),
                            rs.getDouble("budget"),
                            rs.getDate("created_at").toLocalDate(),
                            client
                    );
                }
            }
            throw new ResourceNotFoundException("Project not found with id: " + id);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch project", e);
        }
    }

    @Override
    public void update(int id, Project project) {
        String sql = "UPDATE projects SET title = ?, budget = ? WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, project.getTitle());
            ps.setDouble(2, project.getBudget());
            ps.setInt(3, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Project not found with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update project", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM projects WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Project not found with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete project", e);
        }
    }
}