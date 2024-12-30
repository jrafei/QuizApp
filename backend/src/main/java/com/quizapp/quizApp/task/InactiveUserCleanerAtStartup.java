package com.quizapp.quizApp.task;

import com.quizapp.quizApp.model.beans.User;
import com.quizapp.quizApp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InactiveUserCleanerAtStartup {

    private final UserRepository userRepository;

    @PostConstruct
    public void cleanInactiveUsers() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        // Trouver tous les utilisateurs désactivés depuis plus d'un mois
        List<User> usersToDelete = userRepository.findByIsActiveFalseAndDeactivationDateBefore(oneMonthAgo);

        // Supprimer les utilisateurs
        for (User user : usersToDelete) {
            deleteUserData(user);
            userRepository.delete(user);
            System.out.println("Utilisateur supprimé au démarrage : " + user.getEmail());
        }
    }

    private void deleteUserData(User user) {
        System.out.println("Suppression des données associées pour l'utilisateur : " + user.getEmail());
    }
}
