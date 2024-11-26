package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
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

    @NotNull
    @Size(min = 8)
    @Column(name = "password", nullable = false)
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
    private boolean isActive = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    public enum Role {
        ADMIN, TRAINEE
    }
}
