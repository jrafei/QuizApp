package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.QuizDTO;

import java.util.List;

public interface QuizService {

    Quiz createQuiz(QuizDTO quizDTO);
    List<Quiz> getAllQuizzes();

}
