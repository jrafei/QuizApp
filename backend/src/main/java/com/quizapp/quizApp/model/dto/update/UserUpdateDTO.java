package com.quizapp.quizApp.model.dto.update;

import com.quizapp.quizApp.model.beans.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    private String firstname; // Optionnel
    private String lastname; // Optionnel

    @Email(message = "L'email doit être valide.")
    private String email; // Optionnel

    @Size(min = 12, message = "Le mot de passe doit contenir au moins 12 caractères.")
    private String password; // Optionnel

    private Role role; // Optionnel
    private String phone; // Optionnel
    private String company; // Optionnel
    private Boolean isActive; // Optionnel
}
