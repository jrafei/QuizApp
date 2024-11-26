package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;


import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface UserService {

    UserDTO createUser(UserDTO user);
    //User getUserByUsername(String username);

    List<User> getAllUsers();
    String deleteUser(Integer id);
    User updatePartialUser( Integer id, Map<String, Object> updatePartialUser);
    String setActiveStatus(Integer id, boolean status);
    String promoteToAdmin(Integer id);
}

