package com.quizapp.quizApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletedRecordDTO {
    private UUID recordId;
    private String quizName;
    private int score;
    private int duration;
    private UUID themeId;
    private UUID quizId;
}

