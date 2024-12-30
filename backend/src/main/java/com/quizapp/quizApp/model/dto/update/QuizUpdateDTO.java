package com.quizapp.quizApp.model.dto.update;

import com.quizapp.quizApp.model.dto.creation.QuestionCreateDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizUpdateDTO {
    private String name;
    private Integer position;
    //private UUID creatorId;
    private UUID themeId;
    //private List<QuestionCreateDTO> questions; // Liste des questions associées

}
