package com.quizapp.quizApp.model.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizRankingStatsDTO {
    private String firstname;
    private String lastname;
    private int score;
    private int runtime;
}
