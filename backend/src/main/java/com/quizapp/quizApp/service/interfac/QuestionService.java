package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.dto.QuestionDTO;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    List<QuestionDTO> getAllQuestions();
    List<QuestionDTO> getActiveQuestionsByQuiz(UUID quizId);
    List<QuestionDTO> getAllQuestionsByQuiz(UUID quizId);
    QuestionDTO getQuestionById(UUID id);
    QuestionDTO createQuestion(QuestionDTO questionDTO);
    QuestionDTO updateQuestion(UUID id, QuestionDTO questionDTO);
    void deleteQuestion(UUID id);
    void activateQuestion(UUID id);
    void deactivateQuestion(UUID id);
}