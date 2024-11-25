package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.model.dto.UserDTO;
import com.quizapp.quizApp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService; // On passe par l'interface pour utiliser les méthodes de UserServiceImpl

    // Ajout d'un utilisateur
    @PostMapping(value = "/create" )
    public UserDTO createUser(@Valid @RequestBody UserDTO userdto) {
        System.out.println("Validation en cours pour : " + userdto);
        return userService.createUser(userdto);
    }

    // Récupération de tous les utilisateurs
    @GetMapping("/read")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    // Modification d'un utilisateur
    @PatchMapping("/update/{id}")
    public User update(@PathVariable int id,  @RequestBody Map<String, Object> updatePartialUser){
        return userService.updatePartialUser(id, updatePartialUser);
    }

    // Activer un user
    @PatchMapping("/activate/{id}")
    public String activateUser(@PathVariable int id) {
        return userService.setActiveStatus(id, true);
    }

    // Désactiver un user
    @PatchMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable int id) {
        return userService.setActiveStatus(id, false);
    }

    // Donner droits admin à un user
    @PatchMapping("/promoteToAdmin/{id}")
    public String promoteToAdmin(@PathVariable int id) {
        return userService.promoteToAdmin(id);
    }

    /*

    // Suppression d'un utilisateur

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.findById(id).map(user -> {
            userService.delete(user);
            return ResponseEntity.noContent().<Void>build();  // Correction ici
        }).orElse(ResponseEntity.notFound().build());
    }

    */

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}
