package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); // Vérification d'unicité

    //@Nullable
    Optional<User> findById(Integer id);
}
