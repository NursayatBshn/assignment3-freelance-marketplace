package model;

import exception.InvalidInputException;

import java.time.LocalDate;

public class Bid implements Validatable {

    private int id;
    private Project project;
    private Freelancer freelancer;
    private double bidAmount;
    private LocalDate bidDate;

    public Bid(int id, Project project, Freelancer freelancer,
               double bidAmount, LocalDate bidDate) {
        this.id = id;
        this.project = project;
        this.freelancer = freelancer;
        this.bidAmount = bidAmount;
        this.bidDate = bidDate;
    }

    @Override
    public void validate() {
        if (project == null) {
            throw new InvalidInputException("Bid must have a project");
        }
        if (freelancer == null) {
            throw new InvalidInputException("Bid must have a freelancer");
        }
        if (bidAmount <= 0) {
            throw new InvalidInputException("Bid amount must be greater than 0");
        }
        if (bidDate == null) {
            throw new InvalidInputException("Bid date is required");
        }
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
