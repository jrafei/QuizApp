package com.quizapp.quizApp.service;

import com.quizapp.quizApp.exception.DuplicateEmailException;
import com.quizapp.quizApp.exception.InvalidRoleException;
import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;
import com.quizapp.quizApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    // conteneur Spring injecte automatiquement l'object userRepository à travers le constructeur 'AllArgsConstructor'
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Validation du mot de passe
        validatePassword(userDTO.getPassword());

        // Validation de l'unicité de l'email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("L'email " + userDTO.getEmail() + " est déjà utilisé.");
        }

        // Validation du rôle
        try {
            User.Role.valueOf(userDTO.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Le rôle " + userDTO.getRole() + " est invalide.");
        }

        User user = modelMapper.map(userDTO, User.class); // mapper vers la classe User
        User savedUser = userRepository.save(user);
        
        return modelMapper.map(savedUser, UserDTO.class);  // mapper vers le dto
    }

    // Méthode de validation du mot de passe
    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        // Vérification de la longueur minimale de 12 caractères
        if (password.length() < 12) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 12 caractères.");
        }

        // Vérification qu'il contient au moins un chiffre
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un chiffre.");
        }

        // Vérification qu'il contient au moins une majuscule
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule.");
        }

        // Vérification qu'il contient au moins un caractère spécial ou signe de ponctuation
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins un caractère spécial ou signe de ponctuation.");
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    // cette méthode nécessite de mettre tout les attributs de User dans requestBody pour mettre à jour un seul champs alorsque celui d'après on met juste les attributs qu'on a besoin à modifier
    public User updateUser(int id, User user) {
        return userRepository.findById(id).map(u-> {
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            //u.setEmail(user.getEmail());
            //u.setPassword(user.getPassword());
            return userRepository.save(u);
        }).orElseThrow(()-> new RuntimeException("User Not Found"));
    }

    public User updatePartialUser(int id, Map<String, Object> updates) {
        // Récupérer l'utilisateur existant depuis la base de données
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'id : " + id);
        }

        User existingUser = optionalUser.get();

        // Parcourir la map des mises à jour
        updates.forEach((key, value) -> {
            switch (key) {
                case "firstname":
                    existingUser.setFirstname((String) value);
                    break;
                case "lastname":
                    existingUser.setLastname((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                case "password":
                    existingUser.setPassword((String) value);
                    break;
                case "company":
                    existingUser.setCompany((String) value);
                    break;
                case "phone":
                    existingUser.setPhone((String) value);
                    break;
                case "isActive":
                    existingUser.setIsActive((Boolean) value);
                    break;
                case "role":
                    existingUser.setRole(User.Role.valueOf((String) value));  // Conversion de chaîne en enum
                    break;
                case "manager":
                    Optional<User> manager = userRepository.findById((Integer) value);
                    if (manager.isPresent()) {
                        existingUser.setManager(manager.get());
                    } else {
                        throw new IllegalArgumentException("Manager non trouvé avec l'id : " + value);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Champ non reconnu : " + key);
            }
        });

        // Sauvegarder l'utilisateur mis à jour dans la base de données
        return userRepository.save(existingUser);
    }

    public String setActiveStatus(int id, boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setIsActive(status);
        userRepository.save(user);
        return status ? "Utilisateur activé" : "Utilisateur désactivé";
    }

    @Override
    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "User successfully deleted ! ";
    }

}


