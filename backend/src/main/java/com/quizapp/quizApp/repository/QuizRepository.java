package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Quiz;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findByCreatorId(UUID creatorId);
    List<Quiz> findByIsActive(Boolean isActive);
    boolean existsByNameAndThemeId(String name, UUID theme_id);
    List<Quiz> findByThemeId(UUID themeId);

    List<Quiz> findByThemeIdAndIsActive(@NotNull(message = "L'ID du thème est obligatoire.") UUID themeId, Boolean b);

}
