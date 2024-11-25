package com.quizapp.quizApp.model.beans;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int nbRespondents;
    private int scoreRank;
    private int bestScore;
    private int durationOfBestScore;

}
