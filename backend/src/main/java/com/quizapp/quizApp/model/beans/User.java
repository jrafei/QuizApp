package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(length = 36, nullable = false, unique = true)
private UUID id;
     */

    @Column(name = "firstname", length = 60, nullable = false)
    private String firstname;

    @Column(name = "lastname",length = 60, nullable = false)
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
    private Timestamp creationDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;



    @ManyToOne
    @JoinColumn(name = "manager_id",nullable = true) // clé étrangère
    private User manager;


    public enum Role {
        ADMIN,
        TRAINEE
    }

    // Méthode qui sera appelée avant la persistance pour initialiser creationDate
    @PrePersist
    protected void onCreate() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

}
