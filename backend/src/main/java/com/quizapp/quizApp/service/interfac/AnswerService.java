package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.AnswerDTO;

import java.util.List;
import java.util.UUID;

public interface AnswerService {

    List<AnswerDTO> getAllAnswers();

    List<AnswerDTO> getActiveAnswersByQuestion(UUID questionId);

    List<AnswerDTO> getAllAnswersByQuestion(UUID questionId);

    AnswerDTO getAnswerById(UUID id);

    AnswerDTO createAnswer(AnswerDTO answerDTO);

    AnswerDTO updateAnswer(UUID id, AnswerDTO answerDTO);

    void setCorrectAnswer(UUID id);

    void deleteAnswer(UUID id);

    void activateAnswer(UUID id);

    void deactivateAnswer(UUID id);
}
