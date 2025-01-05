package com.quizapp.quizApp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponseDTO {
    private UUID id;
    private int score;
    private int duration;

    private UUID traineeId; //creatorId;
    private UUID quizID; //themeId;
}
