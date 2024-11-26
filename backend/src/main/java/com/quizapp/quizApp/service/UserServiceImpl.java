package com.quizapp.quizApp.service;

import com.quizapp.quizApp.exception.DuplicateEmailException;
import com.quizapp.quizApp.exception.InvalidRoleException;
import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;
import com.quizapp.quizApp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.catalina.Manager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    // conteneur Spring injecte automatiquement l'object userRepository à travers le constructeur 'AllArgsConstructor'
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    /*
    @PostConstruct
    public void configureModelMapper() {
        modelMapper.typeMap(UserDTO.class, User.class).addMappings(mapper ->
                mapper.skip(User::setManager)
        );
        modelMapper.addConverter(context -> {
            UUID idManager = context.getSource();
            if (idManager != null) {
                return userRepository.findById(idManager)
                        .orElseThrow(() -> new IllegalArgumentException("Manager non trouvé avec l'id : " + idManager));
            }
            return null;
        }, UUID.class, User.class);
    }
    */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Validation du mot de passe

        System.out.println("debut createUser");
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
        // Validation du manager
        User manager = null;

        if (userDTO.getManager_id() != null) {
            // print id_manager non null
            System.out.println("id manager non null");
            manager = userRepository.findById(userDTO.getManager_id())
                    .orElseThrow(() -> new IllegalArgumentException("Manager non trouvé avec l'id : " + userDTO.getManager_id()));
        }
        else {
            System.out.println("id null");
        }

        user.setManager(manager);
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



    public User updatePartialUser(Integer id, Map<String, Object> updates) {
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

    public String setActiveStatus(Integer id, boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setIsActive(status);
        userRepository.save(user);
        return status ? "Utilisateur activé" : "Utilisateur désactivé";
    }

    public String promoteToAdmin(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);
        return "Utilisateur promu administrateur";
    }

    @Override
    public String deleteUser(Integer id) {
        userRepository.deleteById(id);
        return "User successfully deleted ! ";
    }


    public UUID convertToUUID(String id) {
        if (id.startsWith("0x")) {
            // Supprimer le préfixe "0x" et formater correctement
            id = id.substring(2);
            String formatted = String.format(
                    "%s-%s-%s-%s-%s",
                    id.substring(0, 8),
                    id.substring(8, 12),
                    id.substring(12, 16),
                    id.substring(16, 20),
                    id.substring(20)
            );
            return UUID.fromString(formatted);
        }
        return UUID.fromString(id);
    }
}


