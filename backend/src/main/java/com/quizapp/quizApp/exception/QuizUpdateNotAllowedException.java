package com.quizapp.quizApp.exception;

public class QuizUpdateNotAllowedException  extends RuntimeException {
    public QuizUpdateNotAllowedException (String message) {
        super(message);
    }
}