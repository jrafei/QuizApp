package com.quizapp.quizApp.service;

import com.quizapp.quizApp.model.beans.Theme;

import java.util.List;

public interface ThemeService {
    Theme createTheme(Theme theme);

    List<Theme> getAllThemes();

}
