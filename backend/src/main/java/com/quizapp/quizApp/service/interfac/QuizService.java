package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.model.dto.update.QuizUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface QuizService {
    QuizResponseDTO createQuiz(QuizCreateDTO quizCreateDTO);
    List<QuizResponseDTO> getAllQuizzes();

    List<QuizResponseDTO> getQuizzesByCreator(UUID creatorId);

    //List<QuizResponseDTO> getQuizzesByCreator(UUID creatorId);
    QuizResponseDTO updateQuiz(UUID id, QuizUpdateDTO quizCreateDTO);
    QuizResponseDTO setActiveStatus(UUID id, Boolean isActive);
    void deleteQuiz(UUID id);
    List<QuizResponseDTO> getQuizzesByIsActive(Boolean isActive);
    List<QuizResponseDTO> getQuizzesByTheme(UUID themeId);
}
