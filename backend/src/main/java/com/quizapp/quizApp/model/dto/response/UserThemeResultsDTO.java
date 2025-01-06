package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserThemeResultsDTO {
    private String name;
    private double averageScore;
    private int bestScore;
    private int worstScore;
}

