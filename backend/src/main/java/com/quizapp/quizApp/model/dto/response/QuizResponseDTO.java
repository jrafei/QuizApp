package com.quizapp.quizApp.model.dto.response;

import com.quizapp.quizApp.model.dto.creation.QuestionCreateDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class QuizResponseDTO {
    private UUID id;
    private String name;
    private Boolean isActive;
    private Integer position;
    private LocalDateTime creationDate;
    //private UUID creatorId;
    private UUID themeId;

}
