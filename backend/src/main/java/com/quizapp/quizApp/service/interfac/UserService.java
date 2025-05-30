package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.creation.UserCreateDTO;
import com.quizapp.quizApp.model.dto.response.UserResponseDTO;
import com.quizapp.quizApp.model.dto.update.UserUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserCreateDTO user);

    List<UserResponseDTO> getAllUsers();

    String deleteUser(UUID id);

    Optional<User> getUserByEmail(String email);

    //UserResponseDTO updatePartialUser(UUID id, UserUpdateDTO userUpdateDTO);

    UserResponseDTO updatePartialUser(UUID id, UserUpdateDTO userUpdateDTO, UUID currentUserId, User.Role currentUserRole);

    String setActiveStatus(UUID id, Boolean status);

    String promoteToAdmin(UUID id);
    String demoteToTrainee(UUID id);

    Optional<User> getUserById(UUID id);

    boolean activateUserByToken(String token);

    void forgotPassword(String email);

    boolean isAccountDeactivated(String email);

    void requestAccountReactivation(String email);

    void validateAndReactivateAccount(String email, String validationCode);
}
