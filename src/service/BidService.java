package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import model.Bid;
import repository.BidRepository;

import java.util.List;

public class BidService {
    private final BidRepository repository;

    public BidService(BidRepository repository) {
        this.repository = repository;
    }

    public void create(Bid bid) {
        if (bid == null) {
            throw new InvalidInputException("Bid cannot be null");
        }

        bid.validate();

        int projectId = bid.getProject().getId();
        int freelancerId = bid.getFreelancer().getId();

        if (repository.existsByProjectAndFreelancer(projectId, freelancerId)) {
            throw new DuplicateResourceException("Freelancer has already placed a bid for this project");
        }

        repository.create(bid);
    }

    public List<Bid> getAll() {
        return repository.getAll();
    }

    public Bid getById(int id) {
        return repository.getById(id);
    }

    public void update(int id, Bid bid) {
        bid.validate();
        repository.update(id, bid);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}