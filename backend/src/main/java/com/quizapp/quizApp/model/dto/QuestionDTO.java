package com.quizapp.quizApp.model.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuestionDTO {
    private UUID id;
    private String label;
    private Boolean isActive = false;
    private Integer position = null; // Position dans le quiz si active
    private UUID quizId; // Référence au Quiz parent
    private List<AnswerDTO> answers; // Liste de réponses associées
}
