package AI13.SpringBoot.service;

import AI13.SpringBoot.models.beans.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    //User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(int id ,User user);
    String deleteUser(int id);

}

