package model;

import exception.InvalidInputException;
import java.time.LocalDate;

public class Project implements Validatable {

    private int id;
    private String title;
    private double budget;
    private LocalDate createdAt;
    private Client client;

    public Project(int id, String title, double budget, LocalDate createdAt, Client client) {
        this.id = id;
        this.title = title;
        this.budget = budget;
        this.createdAt = createdAt;
        this.client = client;
    }

    @Override
    public void validate() {
        if (title == null || title.isBlank()) {
            throw new InvalidInputException("Project title cannot be empty");
        }

        if (budget <= 0) {
            throw new InvalidInputException("Project budget must be greater than 0");
        }

        if (client == null) {
            throw new InvalidInputException("Project must have a client");
        }
    }

    public Client getClient() {
        return client;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public double getBudget() {
        return budget;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
