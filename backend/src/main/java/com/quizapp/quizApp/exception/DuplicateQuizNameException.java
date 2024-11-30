package com.quizapp.quizApp.exception;

public class DuplicateQuizNameException extends RuntimeException {
    public DuplicateQuizNameException(String message) {
        super(message);
    }
}