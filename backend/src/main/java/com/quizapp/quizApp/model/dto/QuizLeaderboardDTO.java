package com.quizapp.quizApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizLeaderboardDTO {
    private UUID userId;
    private String userName;
    private int totalScore;
    private double averageDuration; // Durée moyenne pour compléter le quiz
    private int attempts;           // Nombre de participations
    private int rank;               // Position dans le classement
    private double weightedScore;   // Score pondéré (inclut le temps)
}
