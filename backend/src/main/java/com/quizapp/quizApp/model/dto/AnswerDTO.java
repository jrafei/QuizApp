package com.quizapp.quizApp.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDTO {
    private UUID id;
    private String label;
    private Boolean isCorrect = false;
    private Boolean isActive = false;
    private Integer position = null;
    private UUID questionId; // Référence à la Question
}
