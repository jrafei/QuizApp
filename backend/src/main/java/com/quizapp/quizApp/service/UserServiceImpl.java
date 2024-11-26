package com.quizapp.quizApp.service;

import com.quizapp.quizApp.exception.DuplicateEmailException;
import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;
import com.quizapp.quizApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        validatePassword(userDTO.getPassword());

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("L'email " + userDTO.getEmail() + " est déjà utilisé.");
        }

        User.Role role = userDTO.getRole();
        if (role == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire.");
        }

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        if (password.length() < 12) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 12 caractères.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un chiffre.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule.");
        }

        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un caractère spécial.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User updatePartialUser(UUID id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstname":
                    user.setFirstname((String) value);
                    break;
                case "lastname":
                    user.setLastname((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    validatePassword((String) value);
                    user.setPassword((String) value);
                    break;
                case "company":
                    user.setCompany((String) value);
                    break;
                case "phone":
                    user.setPhone((String) value);
                    break;
                case "isActive":
                    user.setActive((Boolean) value);
                    break;
                case "role":
                    user.setRole(User.Role.valueOf((String) value));
                    break;
                default:
                    throw new IllegalArgumentException("Champ non reconnu : " + key);
            }
        });

        return userRepository.save(user);
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
        userRepository.deleteById(id);
        return "Utilisateur supprimé avec succès !";
    }
}
