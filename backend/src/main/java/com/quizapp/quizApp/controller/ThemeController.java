package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.service.ThemeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public List<Theme> getAllThemes(){
        return themeService.getAllThemes();
    }

    @PostMapping("/create")
    public Theme createTheme(@RequestBody Theme theme) {
        return themeService.createTheme(theme);
    }
}
