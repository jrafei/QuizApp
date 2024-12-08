package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.creation.UserCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.model.beans.Record;

import java.util.List;

public interface RecordService {
    RecordResponseDTO createRecord(RecordCreateDTO user);
    int calculateScore(Record record);

    List<RecordResponseDTO> getAllRecords();
}
