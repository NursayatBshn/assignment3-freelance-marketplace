package model;

import exception.InvalidInputException;
import java.time.LocalDate;

public class Freelancer extends BaseUser implements Validatable {

    private double rating;
    private LocalDate joinedAt;

    // Оставляем только полный конструктор для загрузки из БД
    public Freelancer(int id, String firstName, String lastName, String email, double rating, LocalDate joinedAt) {
        super(id, firstName, lastName, email);
        this.rating = rating;
        this.joinedAt = joinedAt;
    }

    // Если тебе нужно создавать фрилансера БЕЗ ID (перед сохранением в БД),
    // можно добавить такой конструктор:
    public Freelancer(String firstName, String lastName, String email, double rating) {
        super(0, firstName, lastName, email);
        this.rating = rating;
        this.joinedAt = LocalDate.now();
    }

    @Override
    public void validate() {
        if (getFirstName() == null || getFirstName().isBlank()) {
            throw new InvalidInputException("Freelancer first name must not be empty");
        }
        if (getLastName() == null || getLastName().isBlank()) {
            throw new InvalidInputException("Freelancer last name must not be empty");
        }
        if (getEmail() == null || !getEmail().contains("@")) {
            throw new InvalidInputException("Freelancer email is invalid");
        }
        if (rating < 0 || rating > 5) {
            throw new InvalidInputException("Freelancer rating must be between 0 and 5");
        }
        if (joinedAt == null) {
            throw new InvalidInputException("Freelancer joined date is required");
        }
    }

    @Override
    public String getRole() {
        return "FREELANCER";
    }

    @Override
    public double getRating() {
        return rating;
    }

    // Геттеры и сеттеры
    public double getRatingValue() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public LocalDate getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDate joinedAt) { this.joinedAt = joinedAt; }
}