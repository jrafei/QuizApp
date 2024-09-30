package AI13.SpringBoot.models;

import java.sql.Timestamp;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String company;
    private String phone;
    private Timestamp creationDate;
    private boolean isActive;

    // Constructeur par défaut
    public User() {}

    // Constructeur avec tous les champs
    public User(String firstname, String lastname, String email, String password, String company, String phone, Timestamp creationDate, boolean isActive) {

        this.id = counter.incrementAndGet();

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.company = company;
        this.phone = phone;
        this.creationDate = creationDate;
        this.isActive = isActive;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
