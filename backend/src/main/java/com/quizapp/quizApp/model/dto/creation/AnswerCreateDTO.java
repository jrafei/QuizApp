package com.quizapp.quizApp.model.dto.creation;

import jakarta.transaction.Transactional;
import lombok.Data;

import java.util.UUID;

@Data
@Transactional
public class AnswerCreateDTO {
        private String label;
        private Boolean isCorrect = false;
        private Boolean isActive = false;
        private Integer position = null;
        private UUID questionId; // Référence à la Question
}