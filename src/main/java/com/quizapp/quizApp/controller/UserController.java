package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.service.UserService;
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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Récupération de tous les utilisateurs
    @GetMapping("/read")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    // Modification d'un utilisateur
    @PatchMapping("/update/{id}")
    public User update(@PathVariable long id,  @RequestBody Map<String, Object> updatePartialUser){
        return userService.updatePartialUser(id, updatePartialUser);
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
    public String delete(@PathVariable long id) {
        return userService.deleteUser(id);
    }

}
