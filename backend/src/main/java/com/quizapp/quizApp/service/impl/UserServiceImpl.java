package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.DuplicateEmailException;
import com.quizapp.quizApp.exception.UserNotFoundException;
import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.creation.UserCreateDTO;
import com.quizapp.quizApp.model.dto.response.UserResponseDTO;
import com.quizapp.quizApp.model.dto.update.UserUpdateDTO;
import com.quizapp.quizApp.repository.UserRepository;
import com.quizapp.quizApp.service.interfac.UserService;
import com.quizapp.quizApp.util.PasswordValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator passwordValidator;

    @Override
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        userCreateDTO.setEmail(userCreateDTO.getEmail().toLowerCase());
        passwordValidator.validate(userCreateDTO.getPassword());

        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("L'email " + userCreateDTO.getEmail() + " est déjà utilisé.");
        }

        User.Role role = userCreateDTO.getRole();
        if (role == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire.");
        }

        User user = modelMapper.map(userCreateDTO, User.class);
        user.setActive(false);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'id : " + id)));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }

    @Override
    public UserResponseDTO updatePartialUser(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));

        if (userUpdateDTO.getFirstname() != null) {
            user.setFirstname(userUpdateDTO.getFirstname());
        }
        if (userUpdateDTO.getLastname() != null) {
            user.setLastname(userUpdateDTO.getLastname());
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPassword() != null) {
            passwordValidator.validate(userUpdateDTO.getPassword());
            user.setPassword(userUpdateDTO.getPassword());
        }
        if (userUpdateDTO.getPhone() != null) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getCompany() != null) {
            user.setCompany(userUpdateDTO.getCompany());
        }
        if (userUpdateDTO.getRole() != null) {
            user.setRole(userUpdateDTO.getRole());
        }
        if (userUpdateDTO.getIsActive() != null) {
            user.setActive(userUpdateDTO.getIsActive());
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public String setActiveStatus(UUID id, boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setActive(status);
        userRepository.save(user);
        return status ? "Utilisateur activé" : "Utilisateur désactivé";
    }

    @Override
    public String promoteToAdmin(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);
        return "Utilisateur promu administrateur";
    }

    @Override
    public String demoteToTrainee(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));

        if (user.getRole() == User.Role.TRAINEE) {
            return "L'utilisateur est déjà un stagiaire.";
        }

        user.setRole(User.Role.TRAINEE);
        userRepository.save(user);

        return "Les droits administrateur ont été retirés à l'utilisateur.";
    }


    @Override
    public String deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return "Utilisateur supprimé avec succès !";
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException("Utilisateur non trouvé avec l'id : " + id);
        }
    }

}
