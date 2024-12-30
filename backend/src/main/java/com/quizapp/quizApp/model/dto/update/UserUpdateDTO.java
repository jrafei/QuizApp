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
    private String password; // Optionnel
    private String phone; // Optionnel
    private String company; // Optionnel
}
