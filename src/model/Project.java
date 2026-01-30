package model;

import exception.InvalidInputException;

import java.time.LocalDate;

/**
 * Project entity
 * Represents a freelance project created by a client
 */
public class Project implements Validatable, Payable {

    private int id;
    private String title;
    private double budget;
    private LocalDate createdAt;
    private Client client;

    // ===== Constructors =====

    // For creating new project (before DB insert)
    public Project(String title, double budget, Client client) {
        this.title = title;
        this.budget = budget;
        this.client = client;
        this.createdAt = LocalDate.now();
        validate();
    }

    // For reading project from DB
    public Project(int id, String title, double budget, LocalDate createdAt, Client client) {
        this.id = id;
        this.title = title;
        this.budget = budget;
        this.createdAt = createdAt;
        this.client = client;
    }

    public Project(int projectId) {
        this.id = projectId;
    }

    // ===== Validation =====

    @Override
    public void validate() {
        if (title == null || title.isBlank()) {
            throw new InvalidInputException("Project title cannot be empty");
        }
        if (budget <= 0) {
            throw new InvalidInputException("Project budget must be greater than 0");
        }
        if (client == null || client.getId() <= 0) {
            throw new InvalidInputException("Project must have a valid client");
        }
    }

    // ===== Getters & Setters =====

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getBudget() {
        return budget;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setTitle(String title) {
        this.title = title;
        validate();
    }

    public void setBudget(double budget) {
        this.budget = budget;
        validate();
    }

    public void setClient(Client client) {
        this.client = client;
        validate();
    }

    // ===== Utility =====

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", budget=" + budget +
                ", createdAt=" + createdAt +
                ", clientId=" + (client != null ? client.getId() : null) +
                '}';
    }

    @Override
    public double getAmount() {
        return 0;
    }
}
