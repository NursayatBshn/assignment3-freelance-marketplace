package service;

import exception.InvalidInputException;
import model.Freelancer;
import repository.FreelancerRepository;

import java.util.List;

public class FreelancerService {

    private final FreelancerRepository freelancerRepository = new FreelancerRepository();

    public void create(Freelancer freelancer) {

        if (freelancer == null) {
            throw new InvalidInputException("Freelancer cannot be null");
        }

        freelancer.validate();

        freelancerRepository.create(freelancer);
    }

    public List<Freelancer> getAll() {
        return freelancerRepository.getAll();
    }

    public Freelancer getById(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Freelancer id must be positive");
        }

        return freelancerRepository.getById(id);
    }

    public Freelancer createIfNotExist(Freelancer freelancer) {
        // 1. Сначала ищем по email
        Freelancer existing = freelancerRepository.findByEmail(freelancer.getEmail());

        if (existing != null) {
            // Если нашли — возвращаем его, программа не падает
            return existing;
        }

        // 2. Если не нашли — создаем
        freelancerRepository.create(freelancer);

        // После создания нужно получить его из базы, чтобы узнать присвоенный ID
        return freelancerRepository.findByEmail(freelancer.getEmail());
    }

    public void update(int id, Freelancer freelancer) {

        if (id <= 0) {
            throw new InvalidInputException("Freelancer id must be positive");
        }

        if (freelancer == null) {
            throw new InvalidInputException("Freelancer cannot be null");
        }

        freelancer.validate();

        freelancerRepository.update(id, freelancer);
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Freelancer id must be positive");
        }

        freelancerRepository.delete(id);
    }
}
