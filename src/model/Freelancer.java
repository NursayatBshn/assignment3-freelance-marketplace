package model;

import java.time.LocalDate;

public class Freelancer extends BaseUser {

    private double rating;
    private LocalDate joinedAt;
    private String phone;

    public Freelancer(int id, String firstName, String lastName, String email,
                      double rating, LocalDate joinedAt, String phone) {
        super(id, firstName, lastName, email);
        this.rating = rating;
        this.joinedAt = joinedAt;
        this.phone = phone;
    }

    @Override
    public String getRole() {
        return "FREELANCER";
    }

    @Override
    public double getRating() {
        return rating;
    }
}
