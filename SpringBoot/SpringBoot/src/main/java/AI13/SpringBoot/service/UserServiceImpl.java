package AI13.SpringBoot.service;


import AI13.SpringBoot.models.beans.User;
import AI13.SpringBoot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    // injnection par constructeur par 'AllArgsConstructor'
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int id, User user) {
        return userRepository.findById(id).map(u-> {
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            //u.setEmail(user.getEmail());
            //u.setPassword(user.getPassword());
            return userRepository.save(u);
        }).orElseThrow(()-> new RuntimeException("User Not Found"));
    }

    @Override
    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "User successfully deleted! ";
    }

}
