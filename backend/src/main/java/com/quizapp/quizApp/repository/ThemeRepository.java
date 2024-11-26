package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, UUID> {
    Optional<Theme> findById(UUID themeId);
}
