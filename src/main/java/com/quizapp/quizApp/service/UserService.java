package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.User;


import java.util.List;
import java.util.Map;


public interface UserService {

    User createUser(User user);
    //User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(int id ,User user);
    String deleteUser(int id);
    User updatePartialUser( int id, Map<String, Object> updatePartialUser);
}

