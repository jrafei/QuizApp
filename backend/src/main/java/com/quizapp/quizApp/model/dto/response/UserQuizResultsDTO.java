package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizResultsDTO {
    private String quizName;
    private double averageScore;
    private int countQuestions;
    private int bestScore;
    private int worstScore;
}

