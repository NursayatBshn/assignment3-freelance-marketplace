package model;

import exception.InvalidInputException;

import java.time.LocalDate;

public class Client extends BaseUser implements Validatable {

    private LocalDate registeredAt;

    public Client(int id, String firstName, String lastName, String email, LocalDate registeredAt) {
        super(id, firstName, lastName, email);
        this.registeredAt = registeredAt;
    }

    @Override
    public void validate() {

        if (getFirstName() == null || getFirstName().isBlank()) {
            throw new InvalidInputException("Client first name must not be empty");
        }

        if (getLastName() == null || getLastName().isBlank()) {
            throw new InvalidInputException("Client last name must not be empty");
        }

        if (getEmail() == null || !getEmail().contains("@")) {
            throw new InvalidInputException("Client email is invalid");
        }

        if (registeredAt == null) {
            throw new InvalidInputException("Client registration date is required");
        }
    }

    @Override
    public String getRole() {
        return "CLIENT";
    }

    @Override
    public double getRating() {
        return 0;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }
    public String getFirstName() {
        return super.getFirstName();
    }
    public String getLastName() {
        return super.getLastName();
    }
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
