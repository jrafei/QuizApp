package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Theme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, UUID> {
    Optional<Theme> findById(UUID themeId);

    boolean existsByTitle(@NotBlank(message = "Le titre du thème est obligatoire.") @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères.") String title);
}
