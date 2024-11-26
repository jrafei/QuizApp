package com.quizapp.quizApp.model.dto;
import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
@Setter
public class QuizDTO {
    private String title;
    private boolean isActive;
    private UUID themeId; // ID du thème qui sera envoyé dans la requête
}