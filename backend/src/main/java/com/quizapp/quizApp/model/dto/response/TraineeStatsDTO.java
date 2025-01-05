package com.quizapp.quizApp.model.dto.response;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TraineeStatsDTO {
    private UUID id;
    private String firstname;
    private String lastname;
}
