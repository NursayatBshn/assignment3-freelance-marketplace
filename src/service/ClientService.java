package service;

import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Client;
import repository.ClientRepository;

import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository = new ClientRepository();

    public void create(Client client) {

        if (client == null) {
            throw new InvalidInputException("Client cannot be null");
        }

        client.validate();

        ClientRepository.create(client);
    }

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public Client getById(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Client id must be positive");
        }

        return clientRepository.getById(id);
    }

    public void update(int id, Client client) {

        if (id <= 0) {
            throw new InvalidInputException("Client id must be positive");
        }

        if (client == null) {
            throw new InvalidInputException("Client cannot be null");
        }

        client.validate();

        clientRepository.update(id, client);
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Client id must be positive");
        }

        clientRepository.delete(id);
    }
}
