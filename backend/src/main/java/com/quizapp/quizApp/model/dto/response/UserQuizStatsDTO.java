package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizStatsDTO {
    private UUID id;
    private String name;    // Nom du quiz
    private int score;      // Score obtenu
    private int duration;   // Durée passée sur le quiz

    private String email;
}
