package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.service.ThemeService;
import com.quizapp.quizApp.util.UUIDUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    // Création d'un thème
    @PostMapping("/create")
    public Theme createTheme(@Valid @RequestBody Theme theme) {
        return themeService.createTheme(theme);
    }

    // Récupération de tous les thèmes
    @GetMapping("/read")
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    // Récupération d'un thème par son ID
    @GetMapping("/{id}")
    public Theme getThemeById(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return themeService.getAllThemes().stream()
                .filter(theme -> theme.getId().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
    }

    // Mise à jour d'un thème
    @PatchMapping("/update/{id}")
    public Theme updateTheme(@PathVariable String id, @RequestBody Theme theme) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return themeService.updateTheme(uuid, theme);
    }

    // Activation d'un thème
    @PatchMapping("/activate/{id}")
    public Theme activateTheme(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return themeService.setActiveStatus(uuid, true);
    }

    // Désactivation d'un thème
    @PatchMapping("/deactivate/{id}")
    public Theme deactivateTheme(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return themeService.setActiveStatus(uuid, false);
    }

    // Suppression d'un thème
    @DeleteMapping("/delete/{id}")
    public String deleteTheme(@PathVariable String id) {
        UUID uuid = UUIDUtil.convertHexToUUID(id);
        return themeService.deleteTheme(uuid);
    }
}
