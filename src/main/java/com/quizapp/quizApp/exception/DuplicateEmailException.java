package com.quizapp.quizApp.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message); // Appelle le constructeur de RuntimeException avec le message
    }
}
