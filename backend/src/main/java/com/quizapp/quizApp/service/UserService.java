package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserDTO user);

    List<User> getAllUsers();

    String deleteUser(UUID id);

    User updatePartialUser(UUID id, Map<String, Object> updatePartialUser);

    String setActiveStatus(UUID id, boolean status);

    String promoteToAdmin(UUID id);
    String demoteToTrainee(UUID id);

    Optional<User> getUserById(UUID id);
}
