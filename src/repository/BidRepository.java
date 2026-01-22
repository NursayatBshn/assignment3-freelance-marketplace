package repository;

import exception.DatabaseOperationException;
import model.Bid;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
