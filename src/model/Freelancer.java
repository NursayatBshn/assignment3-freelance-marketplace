package model;

import exception.InvalidInputException;
import model.interfaces.Validatable;

import java.time.LocalDate;

public class Freelancer extends BaseUser implements Validatable<Freelancer> {
    private double rating;
    private LocalDate joinedAt;

    public Freelancer(int id, String firstName, String lastName, String email, double rating, LocalDate joinedAt) {
        super(id, firstName, lastName, email);
        this.rating = rating;
        this.joinedAt = joinedAt;
    }

    @Override
    public void validate() {
        Validatable.checkStringNotBlank(getFirstName(), "First name");
        Validatable.checkStringNotBlank(getLastName(), "Last name");
        Validatable.checkStringNotBlank(getEmail(), "Email");

        if (rating < 0 || rating > 5) {
            throw new InvalidInputException("Rating must be between 0 and 5");
        }

        if (joinedAt == null) {
            throw new InvalidInputException("Joined date is required");
        }
    }

    @Override
    public String getRole() {
        return "FREELANCER";
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }
}