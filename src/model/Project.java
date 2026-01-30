package model;

import exception.InvalidInputException;
import model.interfaces.Payable;
import model.interfaces.Validatable;

import java.time.LocalDate;

public class Project extends BaseEntity implements Validatable<Project>, Payable {
    private String title;
    private double budget;
    private LocalDate createdAt;
    private Client client;

    public Project(int id, String title, double budget, LocalDate createdAt, Client client) {
        super(id);
        this.title = title;
        this.budget = budget;
        this.createdAt = createdAt;
        this.client = client;
    }

    @Override
    public void validate() {
        Validatable.checkStringNotBlank(title, "Project title");

        if (budget <= 0) {
            throw new InvalidInputException("Project budget must be greater than 0");
        }

        if (client == null || client.getId() <= 0) {
            throw new InvalidInputException("Project must have a valid client");
        }
    }

    @Override
    public String getEntityName() {
        return title;
    }

    @Override
    public String getFullDescription() {
        return "Project: " + title + " | Budget: " + budget + " | Client: " + client.getEntityName();
    }

    @Override
    public double getAmount() {
        return budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Client getClient() {
        return client;
    }
}