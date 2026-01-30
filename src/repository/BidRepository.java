package repository;

import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.Bid;
import model.Client;
import model.Freelancer;
import model.Project;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidRepository {

    public void create(Bid bid) {
        String sql = """
            INSERT INTO bids (project_id, freelancer_id, bid_amount, bid_date)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bid.getProject().getId());
            ps.setInt(2, bid.getFreelancer().getId());
            ps.setDouble(3, bid.getBidAmount());
            ps.setDate(4, Date.valueOf(bid.getBidDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create bid", e);
        }
    }
    public List<Bid> getAll() {
        List<Bid> bids = new ArrayList<>();

        // Выбираем данные из всех связанных таблиц
        String sql = """
        SELECT
            b.bid_id, b.bid_amount, b.bid_date,
            f.freelancer_id, f.first_name AS f_name, f.last_name AS f_last, f.email AS f_email, f.rating, f.joined_at,
            p.project_id, p.title, p.budget, p.created_at,
            c.client_id, c.first_name AS c_name, c.last_name AS c_last, c.email AS c_email, c.registered_at
        FROM bids b
        JOIN freelancers f ON b.freelancer_id = f.freelancer_id
        JOIN projects p ON b.project_id = p.project_id
        JOIN clients c ON p.client_id = c.client_id;
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // 1. Создаем Client (нужен для Project)
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("c_name"),
                        rs.getString("c_last"),
                        rs.getString("c_email"),
                        rs.getDate("registered_at").toLocalDate()
                );

                // 2. Создаем Project
                Project project = new Project(
                        rs.getInt("project_id"),
                        rs.getString("title"),
                        rs.getDouble("budget"),
                        rs.getDate("created_at").toLocalDate(),
                        client
                );

                // 3. Создаем Freelancer (теперь 6 аргументов, как в твоем Freelancer.java)
                Freelancer freelancer = new Freelancer(
                        rs.getInt("freelancer_id"),
                        rs.getString("f_name"),
                        rs.getString("f_last"),
                        rs.getString("f_email"),
                        rs.getDouble("rating"),
                        rs.getDate("joined_at").toLocalDate()
                );

                // 4. Создаем и добавляем Bid
                bids.add(new Bid(
                        rs.getInt("bid_id"),
                        project,
                        freelancer,
                        rs.getDouble("bid_amount"),
                        rs.getDate("bid_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch all bids", e);
        }

        return bids;
    }
    public Bid getById(int id) {
        // Объединяем 4 таблицы, чтобы собрать объект Bid целиком
        String sql = """
            SELECT
                b.bid_id, b.bid_amount, b.bid_date,
                f.freelancer_id, f.first_name AS f_name, f.last_name AS f_last, f.email AS f_email, f.rating, f.joined_at,
                p.project_id, p.title, p.budget, p.created_at,
                c.client_id, c.first_name AS c_name, c.last_name AS c_last, c.email AS c_email, c.registered_at
            FROM bids b
            JOIN freelancers f ON b.freelancer_id = f.freelancer_id
            JOIN projects p ON b.project_id = p.project_id
            JOIN clients c ON p.client_id = c.client_id
            WHERE b.bid_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Собираем клиента для проекта
                    Client client = new Client(
                            rs.getInt("client_id"),
                            rs.getString("c_name"),
                            rs.getString("c_last"),
                            rs.getString("c_email"),
                            rs.getDate("registered_at").toLocalDate()
                    );

                    // 2. Собираем проект
                    Project project = new Project(
                            rs.getInt("project_id"),
                            rs.getString("title"),
                            rs.getDouble("budget"),
                            rs.getDate("created_at").toLocalDate(),
                            client
                    );

                    // 3. Собираем фрилансера (теперь с 6 аргументами!)
                    Freelancer freelancer = new Freelancer(
                            rs.getInt("freelancer_id"),
                            rs.getString("f_name"),
                            rs.getString("f_last"),
                            rs.getString("f_email"),
                            rs.getDouble("rating"),
                            rs.getDate("joined_at").toLocalDate()
                    );

                    // 4. Возвращаем итоговую ставку
                    return new Bid(
                            rs.getInt("bid_id"),
                            project,
                            freelancer,
                            rs.getDouble("bid_amount"),
                            rs.getDate("bid_date").toLocalDate()
                    );
                }
            }
            throw new ResourceNotFoundException("Bid not found with id: " + id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch bid by id", e);
        }
    }
    public void update(int id, Bid bid) {

        String sql = """
        UPDATE bids
        SET bid_amount = ?
        WHERE bid_id = ?;
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, bid.getBidAmount());
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Bid not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update bid", e);
        }
    }
    public void delete(int id) {

        String sql = "DELETE FROM bids WHERE bid_id = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Bid not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete bid", e);
        }
    }
    public boolean existsByProjectAndFreelancer(int projectId, int freelancerId) {

        String sql = """
        SELECT 1
        FROM bids
        WHERE project_id = ? AND freelancer_id = ?;
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setInt(2, freelancerId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check duplicate bid", e);
        }
    }

}
