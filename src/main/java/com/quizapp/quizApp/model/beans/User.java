package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", length = 60, nullable = false)
    private String firstname;

    @Column(length = 60, nullable = false)
    private String lastname;

    @Column(length = 90, unique = true, nullable = false)
    private String email;

    @Column(length = 56, nullable = false)
    private String password;

    @Column(length = 90)
    private String company;

    @Column(length = 13)
    private String phone;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_manager") // clé étrangère
    private User manager;


    public enum Role {
        ADMIN,
        TRAINEE
    }

    // Méthode qui sera appelée avant la persistance pour initialiser creationDate
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

}
