package com.quizapp.quizApp.model.beans;

import com.quizapp.quizApp.model.iterator.Container;
import com.quizapp.quizApp.model.iterator.Iterator;
import com.quizapp.quizApp.model.iterator.QuestionIterator;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "QUIZ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Quiz implements Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "id_theme")
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "id_creator") // clé etrangere
    private User creator;

    @ManyToMany(mappedBy = "quizzes")
    private Set<User> users;


    //mappedBy = "quiz" signifie que cette relation est déjà mappée par l'attribut quiz de la classe Question
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @Override
    public Iterator getIterator() {
        return new QuestionIterator(questions);
    }

    /* Méthode qui sera appelée avant la persistance pour initialiser creationDate */
    @PrePersist
    protected void onCreate() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }


}
