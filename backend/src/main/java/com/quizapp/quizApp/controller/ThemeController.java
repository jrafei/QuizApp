package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.dto.creation.ThemeCreateDTO;
import com.quizapp.quizApp.model.dto.response.ThemeResponseDTO;
import com.quizapp.quizApp.model.dto.update.ThemeUpdateDTO;
import com.quizapp.quizApp.service.interfac.ThemeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<ThemeResponseDTO> createTheme(@Valid @RequestBody ThemeCreateDTO themeCreateDTO) {
        ThemeResponseDTO createdTheme = themeService.createTheme(themeCreateDTO);
        return ResponseEntity.status(201).body(createdTheme); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDTO>> getAllThemes() {
        List<ThemeResponseDTO> themes = themeService.getAllThemes();
        return ResponseEntity.ok(themes); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponseDTO> getThemeById(@PathVariable UUID id) {
        ThemeResponseDTO theme = themeService.getThemeById(id);
        return ResponseEntity.ok(theme); // 200 OK
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ThemeResponseDTO> updateTheme(
            @PathVariable UUID id,
            @RequestBody ThemeUpdateDTO themeUpdateDTO) {
        ThemeResponseDTO updatedTheme = themeService.updateTheme(id, themeUpdateDTO);
        return ResponseEntity.ok(updatedTheme); // 200 OK
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ThemeResponseDTO> activateTheme(@PathVariable UUID id) {
        ThemeResponseDTO activatedTheme = themeService.setActiveStatus(id, true);
        return ResponseEntity.ok(activatedTheme); // 200 OK
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ThemeResponseDTO> deactivateTheme(@PathVariable UUID id) {
        ThemeResponseDTO deactivatedTheme = themeService.setActiveStatus(id, false);
        return ResponseEntity.ok(deactivatedTheme); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable UUID id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
