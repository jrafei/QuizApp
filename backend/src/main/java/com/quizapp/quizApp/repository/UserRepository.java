package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Recherche un utilisateur par email
    Optional<User> findByEmail(String email);

    Optional<User> findByActivationToken(String token);
}
