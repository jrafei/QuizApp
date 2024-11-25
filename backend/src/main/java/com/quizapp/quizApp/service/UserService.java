package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;


import java.util.List;
import java.util.Map;


public interface UserService {

    UserDTO createUser(UserDTO user);
    //User getUserByUsername(String username);

    List<User> getAllUsers();
    User updateUser(int id ,User user);
    String deleteUser(int id);
    User updatePartialUser( int id, Map<String, Object> updatePartialUser);
    String setActiveStatus(int id, boolean status);
    String promoteToAdmin(int id);
}

