package model;

import java.time.LocalDate;

public class Client extends BaseUser {

    private LocalDate registeredAt;

    public Client(int id, String firstName, String lastName, String email, LocalDate registeredAt) {
        super(id, firstName, lastName, email);
        this.registeredAt = registeredAt;
    }

    @Override
    public String getRole() {
        return "CLIENT";
    }

    @Override
    public double getRating() {
        return 0; // клиенты не имеют рейтинга
    }
}
