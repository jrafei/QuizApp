package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.Theme;

import java.util.List;
import java.util.UUID;

public interface ThemeService {
    Theme createTheme(Theme theme);
    List<Theme> getAllThemes();
    Theme updateTheme(UUID id, Theme theme);
    String deleteTheme(UUID id);
    Theme setActiveStatus(UUID id, boolean status);
}
