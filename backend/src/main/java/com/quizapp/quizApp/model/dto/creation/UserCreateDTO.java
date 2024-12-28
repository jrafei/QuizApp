package com.quizapp.quizApp.model.dto.creation;

import com.quizapp.quizApp.model.beans.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire.")
    private String lastname;

    @Email(message = "L'email doit être valide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotNull(message = "Le rôle est obligatoire.")
    private Role role;

    private String phone; // Nullable, conforme au modèle
    private String company; // Nullable, conforme au modèle
}

