package com.quizapp.quizApp.model.dto;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class QuizDTO {
    private String title;
    private boolean isActive;
    private int themeId; // ID du thème qui sera envoyé dans la requête
}