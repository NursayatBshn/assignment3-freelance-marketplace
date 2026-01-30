package model;

import exception.InvalidInputException;

import java.time.LocalDate;

public class Bid implements Payable, Validatable {

    private int id;
    private Project project;
    private Freelancer freelancer;
    private double bidAmount;
    private LocalDate bidDate;

    public Bid(int id,
               Project project,
               Freelancer freelancer,
               double bidAmount,
               LocalDate bidDate) {

        this.id = id;
        this.project = project;
        this.freelancer = freelancer;
        this.bidAmount = bidAmount;
        this.bidDate = bidDate;
    }

    // ===== Payable =====
    @Override
    public double getAmount() {
        return bidAmount;
    }

    // ===== Validatable =====
    @Override
    public void validate() {

        if (project == null || project.getId() <= 0) {
            throw new InvalidInputException("Bid must have valid project");
        }

        if (freelancer == null || freelancer.getId() <= 0) {
            throw new InvalidInputException("Bid must have valid freelancer");
        }

        if (bidAmount <= 0) {
            throw new InvalidInputException("Bid amount must be positive");
        }

        if (bidDate == null) {
            throw new InvalidInputException("Bid date is required");
        }
    }

    // ===== GETTERS =====
    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public LocalDate getBidDate() {
        return bidDate;
    }
}
