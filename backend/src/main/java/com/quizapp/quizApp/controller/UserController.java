package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;
import com.quizapp.quizApp.service.UserService;
import com.quizapp.quizApp.util.UUIDUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService; // On passe par l'interface pour utiliser les méthodes de UserServiceImpl

    // Ajout d'un utilisateur
    @PostMapping(value = "/create")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDto) {
        System.out.println("Validation en cours pour : " + userDto);
        return userService.createUser(userDto);
    }

    // Récupération de tous les utilisateurs
    @GetMapping("/read")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // Récupération d'un utilisateur par son ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return userService.getUserById(uuid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
    }

    // Modification d'un utilisateur
    @PatchMapping("/update/{id}")
    public User update(@PathVariable String id, @RequestBody Map<String, Object> updatePartialUser) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.updatePartialUser(uuid, updatePartialUser);
    }

    // Activer un utilisateur
    @PatchMapping("/activate/{id}")
    public String activateUser(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.setActiveStatus(uuid, true);
    }

    // Désactiver un utilisateur
    @PatchMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.setActiveStatus(uuid, false);
    }

    // Donner les droits d'administrateur à un utilisateur
    @PatchMapping("/promoteToAdmin/{id}")
    public String promoteToAdmin(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.promoteToAdmin(uuid);
    }

    // Retirer les droits d'administrateur à un utilisateur
    @PatchMapping("/demoteToTrainee/{id}")
    public String demoteToTrainee(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.demoteToTrainee(uuid);
    }

    // Suppression d'un utilisateur
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id); // Conversion de l'ID
        return userService.deleteUser(uuid);
    }

}
