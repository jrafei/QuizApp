package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.User;
import com.quizapp.quizApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Récupération de tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupération d'un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Ajout d'un utilisateur
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setCreationDate(LocalDateTime.now());  // Définit la date de création lors de l'ajout d'un utilisateur
        return userRepository.save(user);
    }

    // Modification d'un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());
            user.setEmail(updatedUser.getEmail());
            user.setCompany(updatedUser.getCompany());
            user.setPhone(updatedUser.getPhone());
            user.setIsActive(updatedUser.getIsActive());
            user.setRole(updatedUser.getRole());
            user.setManager(updatedUser.getManager());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Suppression d'un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.noContent().<Void>build();  // Correction ici
        }).orElse(ResponseEntity.notFound().build());
    }
}
