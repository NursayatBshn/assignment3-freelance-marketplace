package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import repository.BidRepository;
import model.Bid;

import java.util.List;

public class BidService {

    private final BidRepository bidRepository = new BidRepository();

    public void create(Bid bid) {

        if (bid == null) {
            throw new InvalidInputException("Bid cannot be null");
        }

        bid.validate();

        int projectId = bid.getProject().getId();
        int freelancerId = bid.getFreelancer().getId();

        if (projectId <= 0 || freelancerId <= 0) {
            throw new InvalidInputException("Invalid project or freelancer");
        }

        if (bidRepository.existsByProjectAndFreelancer(projectId, freelancerId)) {
            throw new DuplicateResourceException(
                    "Freelancer has already placed a bid for this project"
            );
        }

        bidRepository.create(bid);
    }

    public List<Bid> getAll() {
        return bidRepository.getAll();
    }

    public Bid getById(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Bid id must be positive");
        }

        return bidRepository.getById(id);
    }

    public void update(int id, Bid bid) {

        if (id <= 0) {
            throw new InvalidInputException("Bid id must be positive");
        }

        bid.validate();
        bidRepository.update(id, bid);
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Bid id must be positive");
        }

        bidRepository.delete(id);
    }
}
