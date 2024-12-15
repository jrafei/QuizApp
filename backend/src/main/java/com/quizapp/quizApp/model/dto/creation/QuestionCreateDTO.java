package com.quizapp.quizApp.model.dto.creation;

import com.quizapp.quizApp.model.dto.creation.AnswerCreateDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
@Data
@Transactional
public class QuestionCreateDTO {
    @NotEmpty
    private String label;
    @NotEmpty
    private List<AnswerCreateDTO> answers;
}
