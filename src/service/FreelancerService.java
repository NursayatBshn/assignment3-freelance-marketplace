package service;

import exception.InvalidInputException;
import model.Freelancer;
import repository.FreelancerRepository;
import repository.interfaces.CrudRepository;

import java.util.List;

public class FreelancerService {
    private final FreelancerRepository repository;

    public FreelancerService(FreelancerRepository repository) {
        this.repository = repository;
    }

    public void create(Freelancer freelancer) {
        if (freelancer == null) {
            throw new InvalidInputException("Freelancer cannot be null");
        }
        freelancer.validate();
        repository.create(freelancer);
    }

    public Freelancer createIfNotExist(Freelancer freelancer) {
        Freelancer existing = repository.findByEmail(freelancer.getEmail());
        if (existing != null) {
            return existing;
        }
        repository.create(freelancer);
        return repository.findByEmail(freelancer.getEmail());
    }

    public List<Freelancer> getAll() {
        return repository.getAll();
    }

    public Freelancer getById(int id) {
        return repository.getById(id);
    }

    public void update(int id, Freelancer freelancer) {
        freelancer.validate();
        repository.update(id, freelancer);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}