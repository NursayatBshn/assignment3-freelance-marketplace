package service;

import exception.DuplicateResourceException;
import exception.DatabaseOperationException;
import model.Bid;
import repository.BidRepository;

import java.sql.SQLException;

public class BidService {

    private final BidRepository repository = new BidRepository();

    public void create(Bid bid) {
        bid.validate();

        try {
            repository.create(bid);
        } catch (DatabaseOperationException e) {

            Throwable cause = e.getCause();

            if (cause instanceof SQLException sqlEx) {
                if ("23505".equals(sqlEx.getSQLState())) {
                    throw new DuplicateResourceException(
                            "Freelancer has already placed a bid for this project"
                    );
                }
            }

            throw e;
        }
    }
}
