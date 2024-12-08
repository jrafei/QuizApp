package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizStatsDTO {
    private String quizName; // Nom du quiz
    private int score;      // Score obtenu
    private int duration;   // Durée passée sur le quiz

    private String email;
}
