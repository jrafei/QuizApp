package com.quizapp.quizApp.model.dto.creation;

import com.quizapp.quizApp.model.dto.QuestionDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class QuizCreateDTO {
    @NotBlank(message = "Le nom du quiz est obligatoire.")
    @Size(max = 100, message = "Le nom du quiz ne doit pas dépasser 100 caractères.")
    private String name;

    @Min(value = 1, message = "L'ordre doit être un entier positif.")
    private Integer position;

//    @NotNull(message = "L'ID du créateur est obligatoire.")
//    private UUID creatorId;

    @NotNull(message = "L'ID du thème est obligatoire.")
    private UUID themeId;

    private List<QuestionCreateDTO> questions; // Liste des questions associées
}
