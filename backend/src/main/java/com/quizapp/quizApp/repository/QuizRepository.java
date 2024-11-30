package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findByCreatorId(UUID creatorId);
    List<Quiz> findByIsActive(boolean isActive);
    boolean existsByNameAndThemeId(String name, UUID theme_id);
    List<Quiz> findByThemeId(UUID themeId);

    List<Quiz> findByThemeIdAndIsActive(@NotNull(message = "L'ID du thème est obligatoire.") UUID themeId, boolean b);
}
