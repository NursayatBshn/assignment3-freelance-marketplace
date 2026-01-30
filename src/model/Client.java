package model;

import exception.InvalidInputException;
import model.interfaces.Validatable;

import java.time.LocalDate;

public class Client extends BaseUser implements Validatable<Client> {
    private LocalDate registeredAt;

    public Client(int id, String firstName, String lastName, String email, LocalDate registeredAt) {
        super(id, firstName, lastName, email);
        this.registeredAt = registeredAt;
    }

    @Override
    public void validate() {
        Validatable.checkStringNotBlank(getFirstName(), "First name");
        Validatable.checkStringNotBlank(getLastName(), "Last name");
        Validatable.checkStringNotBlank(getEmail(), "Email");

        if (registeredAt == null) {
            throw new InvalidInputException("Registration date is required");
        }
    }

    @Override
    public String getRole() {
        return "CLIENT";
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }
}