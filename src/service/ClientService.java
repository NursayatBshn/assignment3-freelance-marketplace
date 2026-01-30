package service;

import exception.InvalidInputException;
import model.Client;
import repository.interfaces.CrudRepository;

import java.util.List;

public class ClientService {
    private final CrudRepository<Client> repository;

    public ClientService(CrudRepository<Client> repository) {
        this.repository = repository;
    }

    public void create(Client client) {
        if (client == null) {
            throw new InvalidInputException("Client cannot be null");
        }

        client.validate();
        repository.create(client);
    }

    public List<Client> getAll() {
        return repository.getAll();
    }

    public Client getById(int id) {
        return repository.getById(id);
    }

    public void update(int id, Client client) {
        client.validate();
        repository.update(id, client);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}