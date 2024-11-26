package com.quizapp.quizApp.model.beans;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;
    private int duration;

    @Column(name = "context_id", length = 36)
    private String contextId;

    @ManyToOne
    @JoinColumn(name="trainee_id",nullable = false)
    private User trainee;

    @ManyToOne
    @JoinColumn(name="quiz_id", nullable = false)
    private Quiz quiz;


    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name="ranking_id", nullable = false)
    private Ranking rank;

}
