package model;

public abstract class BaseUser {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;

    public BaseUser(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public abstract String getRole();
    public abstract double getRating();

    public void printInfo() {
        System.out.println(firstName + " " + lastName + " (" + email + ")");
    }

    public int getId() {
        return id;
    }
}
