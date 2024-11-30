package com.quizapp.quizApp.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        if (password.length() < 12) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 12 caractères.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un chiffre.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule.");
        }

        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un caractère spécial.");
        }
    }
}
