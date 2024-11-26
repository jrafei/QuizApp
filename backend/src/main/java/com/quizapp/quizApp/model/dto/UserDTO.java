package com.quizapp.quizApp.model.dto;

import com.quizapp.quizApp.model.beans.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id; // Inclure l'identifiant si nécessaire pour les opérations de modification

    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire.")
    private String lastname;

    @Email(message = "L'email doit être valide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 12, message = "Le mot de passe doit contenir au moins 12 caractères.")
    private String password;

    @NotNull(message = "Le rôle est obligatoire.")
    private Role role; // Enumération Role pour correspondre au modèle

    private String phone; // Nullable, conforme au modèle

    private String company; // Nullable, conforme au modèle
}
