package com.quizapp.quizApp.model.dto.response;

import com.quizapp.quizApp.model.beans.User.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String phone;
    private String company;
    private Boolean isActive;
}
