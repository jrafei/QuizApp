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

    @ManyToOne
    @JoinColumn(name="question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name="record_id", nullable = false)
    private Record record;

}
