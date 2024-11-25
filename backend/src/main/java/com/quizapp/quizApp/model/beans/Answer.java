package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String label;
    private boolean correct;
    private boolean isActive;
    private int points;

    @ManyToOne
    @JoinColumn(name="question_id", nullable = false)
    private Question question;

}
