package com.quizapp.quizApp.model.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserThemeStatsDTO {
    private UUID id;
    private String name;
    private int totalScore;    // Score total pour le thème
    private int totalDuration; // Durée totale pour le thème
}