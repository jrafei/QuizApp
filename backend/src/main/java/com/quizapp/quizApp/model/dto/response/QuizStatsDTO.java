package com.quizapp.quizApp.model.dto.response;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizStatsDTO {
    private UUID id;
    private String name;
    private int questionCount;
    private double meanscore;
    private double meanruntime;
}
