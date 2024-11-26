package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    @Override
    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @Override
    public Theme updateTheme(UUID id, Theme theme) {
        Theme existingTheme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
        existingTheme.setTitle(theme.getTitle());
        existingTheme.setActive(theme.isActive());
        return themeRepository.save(existingTheme);
    }

    @Override
    public String deleteTheme(UUID id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
        themeRepository.delete(theme);
        return "Thème supprimé avec succès !";
    }

    @Override
    public Theme setActiveStatus(UUID id, boolean status) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
        theme.setActive(status);
        return themeRepository.save(theme);
    }
}
