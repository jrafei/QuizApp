package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;

import java.util.List;
import java.util.UUID;

public interface QuizService {
    QuizResponseDTO createQuiz(QuizCreateDTO quizCreateDTO);
    List<QuizResponseDTO> getAllQuizzes();
    List<QuizResponseDTO> getQuizzesByCreator(UUID creatorId);
    QuizResponseDTO updateQuiz(UUID id, QuizCreateDTO quizCreateDTO);
    QuizResponseDTO setActiveStatus(UUID id, boolean isActive);
    void deleteQuiz(UUID id);
    List<QuizResponseDTO> getQuizzesByIsActive(boolean isActive);
    List<QuizResponseDTO> getQuizzesByTheme(UUID themeId);
}
