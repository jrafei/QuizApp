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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator passwordValidator;

    @Override
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        // Vérification explicite des champs obligatoires
        if (userCreateDTO.getFirstname() == null || userCreateDTO.getFirstname().isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }
        if (userCreateDTO.getLastname() == null || userCreateDTO.getLastname().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire.");
        }
        if (userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        if (userCreateDTO.getRole() == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire.");
        }

        // Validation du mot de passe
        passwordValidator.validate(userCreateDTO.getPassword());

        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(userCreateDTO.getEmail().toLowerCase()).isPresent()) {
            throw new DuplicateEmailException("L'email " + userCreateDTO.getEmail() + " est déjà utilisé.");
        }

        // Mapper le DTO vers l'entité User
        User user = new User();
        user.setFirstname(userCreateDTO.getFirstname());
        user.setLastname(userCreateDTO.getLastname());
        user.setEmail(userCreateDTO.getEmail().toLowerCase());
        user.setPassword(userCreateDTO.getPassword());
        user.setRole(userCreateDTO.getRole());
        user.setIsActive(false); // Par défaut inactif
        user.setCompany(userCreateDTO.getCompany()); // Optionnel
        user.setPhone(userCreateDTO.getPhone()); // Optionnel

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Retourner l'utilisateur créé en tant que DTO
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }


    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
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
        // Récupérer l'utilisateur existant
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'id : " + id));

        // Vérifier et mettre à jour uniquement les champs non nuls dans le DTO
        Optional.ofNullable(userUpdateDTO.getFirstname()).ifPresent(user::setFirstname);
        Optional.ofNullable(userUpdateDTO.getLastname()).ifPresent(user::setLastname);
        Optional.ofNullable(userUpdateDTO.getEmail()).ifPresent(email -> {
            // Valider que l'email est unique
            if (userRepository.findByEmail(email).isPresent() && !user.getEmail().equals(email)) {
                throw new DuplicateEmailException("L'email " + email + " est déjà utilisé.");
            }
            user.setEmail(email.toLowerCase());
        });
        Optional.ofNullable(userUpdateDTO.getPassword()).ifPresent(password -> {
            passwordValidator.validate(password);
            user.setPassword(password);
        });
        Optional.ofNullable(userUpdateDTO.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userUpdateDTO.getCompany()).ifPresent(user::setCompany);
        Optional.ofNullable(userUpdateDTO.getRole()).ifPresent(user::setRole);
        Optional.ofNullable(userUpdateDTO.getIsActive()).ifPresent(user::setIsActive);

        // Sauvegarder l'utilisateur mis à jour
        User updatedUser = userRepository.save(user);

        // Retourner l'utilisateur mis à jour en DTO
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public String setActiveStatus(UUID id, boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setIsActive(status);
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
