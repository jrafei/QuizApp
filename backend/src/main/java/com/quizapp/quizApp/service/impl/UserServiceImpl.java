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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private static final long CODE_EXPIRATION_MINUTES = 15;

    @Override
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(userCreateDTO.getEmail().toLowerCase()).isPresent()) {
            throw new DuplicateEmailException("L'email " + userCreateDTO.getEmail() + " est déjà utilisé.");
        }

        // Mapper le DTO vers l'entité User avec ModelMapper
        User user = modelMapper.map(userCreateDTO, User.class);

        user.setPassword(null);
        user.setIsActive(false); // Par défaut inactif

        // Générer un token d'activation
        String activationToken = generateActivationToken();
        user.setActivationToken(activationToken);

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Envoyer un email de confirmation avec le lien d'activation
        emailService.sendWelcomeEmail(user.getEmail(), user.getFirstname(), activationToken);

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
            String hashedPassword = passwordEncoder.encode(password); // Hacher le mot de passe avant mise à jour
            user.setPassword(hashedPassword);
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
    public String setActiveStatus(UUID id, Boolean status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        user.setIsActive(status);

        if (!status) {
            user.setDeactivationDate(LocalDateTime.now()); // Date de désactivation
        } else {
            user.setDeactivationDate(null); // Réinitialiser si le compte est réactivé
        }

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

    @Override
    public boolean activateUserByToken(String token) {
        // Vérifier si le token d'activation est valide
        User user = userRepository.findByActivationToken(token)
                .orElseThrow(() -> new RuntimeException("Token d'activation invalide"));

        // Activer l'utilisateur
        user.setIsActive(true);
        user.setActivationToken(null); // Supprimer le token après l'activation

        String temporaryPassword = generateTemporaryPassword();
        String hashedPassword = passwordEncoder.encode(temporaryPassword);
        user.setPassword(hashedPassword);

        userRepository.save(user);

        // Envoyer un email avec le mot de passe temporaire
        emailService.sendCredentialsEmail(user.getEmail(), user.getFirstname(), user.getEmail(), temporaryPassword);

        return true;
    }

    public String generateActivationToken() {
        return UUID.randomUUID().toString();
    }

    public String generateTemporaryPassword() {
        // Catégories de caractères
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*";
        String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;

        Random rand = new Random();

        // Garantir que le mot de passe contient au moins une lettre majuscule, une minuscule, un chiffre et un caractère spécial
        StringBuilder password = new StringBuilder();

        password.append(upperCaseLetters.charAt(rand.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(rand.nextInt(lowerCaseLetters.length())));
        password.append(digits.charAt(rand.nextInt(digits.length())));
        password.append(specialCharacters.charAt(rand.nextInt(specialCharacters.length())));

        // Ajouter des caractères supplémentaires pour atteindre la longueur requise (12 caractères)
        for (int i = 4; i < 12; i++) {
            password.append(allCharacters.charAt(rand.nextInt(allCharacters.length())));
        }

        // Mélanger les caractères pour éviter une structure prévisible
        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        List<Character> characters = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(characters);
        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }

    @Override
    public void forgotPassword(String email) {
        // Check if the user exists with this email
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("If this email is associated with an account, you will receive a message."));

        // Generate a temporary password (or reuse the current one)
        String temporaryPassword = generateTemporaryPassword();

        // Hash the temporary password before saving it
        String hashedPassword = passwordEncoder.encode(temporaryPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        // Send an email with the temporary password
        emailService.sendForgotPasswordEmail(user.getEmail(), user.getFirstname(), temporaryPassword);
    }

    @Override
    public boolean isAccountDeactivated(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return !user.getIsActive();
    }


    @Override
    public void requestAccountReactivation(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (user.getIsActive()) {
            throw new IllegalStateException("User account is already active.");
        }

        // Générer un code de validation
        String validationCode = generateValidationCode();
        user.setValidationCode(validationCode);
        user.setValidationCodeExpiration(LocalDateTime.now().plusMinutes(15)); // Expiration dans 15 minutes

        userRepository.save(user);

        // Envoyer l'e-mail avec le code de validation
        emailService.sendValidationCodeEmail(user.getEmail(), user.getFirstname(), validationCode);
    }


    private String generateValidationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Code à 6 chiffres
    }

    @Override
    public void validateAndReactivateAccount(String email, String validationCode) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (user.getValidationCode() == null || !user.getValidationCode().equals(validationCode)) {
            throw new IllegalArgumentException("Invalid validation code.");
        }

        if (user.getValidationCodeExpiration() != null && user.getValidationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Validation code has expired.");
        }

        // Réactiver le compte
        user.setIsActive(true);
        user.setValidationCode(null);
        user.setValidationCodeExpiration(null);

        // Réinitialiser la date de désactivation
        user.setDeactivationDate(null);

        userRepository.save(user);
    }

}
