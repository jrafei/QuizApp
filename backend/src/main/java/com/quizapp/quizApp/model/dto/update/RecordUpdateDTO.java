package com.quizapp.quizApp.model.dto.update;
import java.util.List;
import java.util.UUID;

import com.quizapp.quizApp.model.beans.Record;
import lombok.Data;

@Data
public class RecordUpdateDTO {
    private int score;
    private int duration;
    private Record.RecordStatus status;
    private List<UUID> answerIds;
}
