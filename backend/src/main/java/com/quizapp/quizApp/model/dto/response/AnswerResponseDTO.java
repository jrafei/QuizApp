package com.quizapp.quizApp.model.dto.response;

import com.quizapp.quizApp.model.beans.Question;
import lombok.Data;

import java.util.UUID;

@Data
public class AnswerResponseDTO {
    //private UUID id;
    private String label;
    private Boolean isCorrect = false;
    private Boolean isActive = false;
    private Integer position = null;
    private Question questionId; // Référence à la Question
}
