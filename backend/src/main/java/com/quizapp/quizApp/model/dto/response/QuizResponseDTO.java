package com.quizapp.quizApp.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class QuizResponseDTO {
    private UUID id;
    private String name;
    private boolean isActive;
    private Integer position;
    private LocalDateTime creationDate;
    private UUID creatorId;
    private UUID themeId;
}
