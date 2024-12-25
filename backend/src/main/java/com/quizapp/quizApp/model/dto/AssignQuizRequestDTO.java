package com.quizapp.quizApp.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class AssignQuizRequestDTO {

    @NotNull(message = "Trainee ID is required.")
    private UUID traineeId;

    @NotNull(message = "Quiz ID is required.")
    private UUID quizId;
}


