package model;

public abstract class BaseUser {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;

    protected BaseUser(int id,
                       String firstName,
                       String lastName,
                       String email) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // ===== ABSTRACT METHODS =====

    public abstract String getRole();

    public abstract double getRating();

    // ===== CONCRETE METHOD (POLYMORPHISM) =====

    public void printInfo() {
        System.out.println(
                getRole() + ": " + firstName + " " + lastName + " (" + email + ")"
        );
    }

    // ===== GETTERS / SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
