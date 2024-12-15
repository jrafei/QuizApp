package com.quizapp.quizApp.model.dto.creation;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Transactional
public class AnswerCreateDTO {
    private String label;
    private Boolean correct ;
}
