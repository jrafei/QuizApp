package AI13.SpringBoot.models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity // Indique que cette classe est une entité JPA
@Table(name = "users") // Facultatif : pour personnaliser le nom de la table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID (Auto-incrément)
    private int id;

    @Column(name = "firstname", length = 60, nullable = false) // Mapping de la colonne "firstname"
    private String firstname;

    @Column(name = "lastname", length = 60, nullable = false) // Mapping de la colonne "lastname"
    private String lastname;

    @Column(name = "email", length = 90, nullable = false, unique = true) // Colonne unique pour l'email
    private String email;

    @Column(name = "password", length = 56, nullable = false) // Mapping du mot de passe
    private String password;

    @Column(name = "company", length = 90) // Mapping de la colonne "company"
    private String company;

    @Column(name = "phone", length = 13) // Mapping du téléphone
    private String phone;

    @Column(name = "creation_date", nullable = false) // Mapping de la date de création
    private Timestamp creationDate;

    @Column(name = "is_active", nullable = false) // Mapping pour l'état actif
    private boolean isActive;

    @Enumerated(EnumType.STRING) // Enregistre l'énumération "role" en tant que chaîne de caractères
    @Column(name = "role", nullable = false)
    private Role role;

    @ManyToOne // Relation "un utilisateur a un manager"
    @JoinColumn(name = "manager_id") // Colonne pour le manager
    private User manager;

    // Constructeurs, Getters et Setters
    public User() {}

    public User(String firstname, String lastname, String email, String password, String company, String phone, Timestamp creationDate, boolean isActive, Role role, User manager) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.company = company;
        this.phone = phone;
        this.creationDate = creationDate;
        this.isActive = isActive;
        this.role = role;
        this.manager = manager;
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
