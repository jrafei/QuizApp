package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.exception.UserNotFoundException;
import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.creation.UserCreateDTO;
import com.quizapp.quizApp.model.dto.response.UserResponseDTO;
import com.quizapp.quizApp.model.dto.update.UserUpdateDTO;
import com.quizapp.quizApp.service.impl.EmailService;
import com.quizapp.quizApp.service.interfac.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService; // On passe par l'interface pour utiliser les méthodes de UserServiceImpl
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    // Ajout d'un utilisateur
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO createdUser = userService.createUser(userCreateDTO);
        return ResponseEntity.status(201).body(createdUser); // 201 Created pour indiquer une création réussie
    }

    // Récupération de tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Retourne une liste concise avec un code 200 OK
    }

    // Récupération d'un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        UserResponseDTO userResponse = modelMapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(userResponse);
    }

    // Mise à jour partielle d'un utilisateur
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO updatedUser = userService.updatePartialUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser); // 200 OK pour mise à jour réussie
    }

    // Activer un utilisateur
    @PatchMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable UUID id) {
        String message = userService.setActiveStatus(id, true);
        return ResponseEntity.ok(message); // Retourne 200 OK avec un message
    }

    // Désactiver un utilisateur
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable UUID id) {
        String message = userService.setActiveStatus(id, false);
        return ResponseEntity.ok(message); // Retourne 200 OK avec un message
    }

    // Modifier le rôle d'un utilisateur (promotion ou rétrogradation)
    @PatchMapping("/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable UUID id, @RequestParam("role") String role) {
        String message;
        if ("admin".equalsIgnoreCase(role)) {
            message = userService.promoteToAdmin(id);
        } else if ("trainee".equalsIgnoreCase(role)) {
            message = userService.demoteToTrainee(id);
        } else {
            throw new IllegalArgumentException("Rôle invalide : " + role);
        }
        return ResponseEntity.ok(message);
    }

    // Suppression d'un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = userService.deleteUser(id);
        return ResponseEntity.noContent().header("Message", message).build(); // 204 No Content avec un header pour le message
    }
}
