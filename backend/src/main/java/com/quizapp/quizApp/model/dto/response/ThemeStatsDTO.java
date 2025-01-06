package com.quizapp.quizApp.model.dto.response;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ThemeStatsDTO {
    private UUID id;
    private String title;
    private Double frequency;
}
