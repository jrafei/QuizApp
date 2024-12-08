package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserThemeStatsDTO {
    private String themeName;  // Nom du thème
    private int totalScore;    // Score total pour le thème
    private int totalDuration; // Durée totale pour le thème
}