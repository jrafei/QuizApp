package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Requête pour trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Requête pour récupérer les utilisateurs par rôle
    List<User> findByRole(User.Role role);

    // Requête pour récupérer les utilisateurs actifs
    List<User> findByIsActive(Boolean isActive);

    // Requête pour récupérer les utilisateurs qui ont un manager spécifique
    List<User> findByManager(User manager);

}
