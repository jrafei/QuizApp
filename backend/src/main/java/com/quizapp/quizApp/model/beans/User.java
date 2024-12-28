package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastname;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Size(min = 8)
    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "company", length = 100) // nullable par défaut
    private String company;

    @Column(name = "phone", length = 20) // nullable par défaut
    private String phone;

    @NotNull
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now(); // Valeur par défaut

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

//    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
//    @JsonManagedReference  // Sérialiser les quiz associés à ce créateur
//    private List<Quiz> quizzes; // Liste des quiz créés par cet utilisateur

    public enum Role {
        ADMIN, TRAINEE
    }

    @Column(name = "activation_token", unique = true)
    private String activationToken;

    @Column(name = "validation_code", length = 6)
    private String validationCode;

    @Column(name = "validation_code_expiration")
    private LocalDateTime validationCodeExpiration;

}
