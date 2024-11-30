package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.DuplicateThemeTitleException;
import com.quizapp.quizApp.exception.ThemeNotFoundException;
import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.model.dto.creation.ThemeCreateDTO;
import com.quizapp.quizApp.model.dto.response.ThemeResponseDTO;
import com.quizapp.quizApp.model.dto.update.ThemeUpdateDTO;
import com.quizapp.quizApp.repository.ThemeRepository;
import com.quizapp.quizApp.service.interfac.ThemeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ThemeResponseDTO createTheme(ThemeCreateDTO themeCreateDTO) {
        // Vérification de l'unicité du titre
        if (themeRepository.existsByTitle(themeCreateDTO.getTitle())) {
            throw new DuplicateThemeTitleException(
                    "Un thème avec le titre \"" + themeCreateDTO.getTitle() + "\" existe déjà."
            );
        }

        Theme theme = modelMapper.map(themeCreateDTO, Theme.class);
        theme.setActive(true);
        Theme savedTheme = themeRepository.save(theme);
        return modelMapper.map(savedTheme, ThemeResponseDTO.class);
    }

    @Override
    public List<ThemeResponseDTO> getAllThemes() {
        return themeRepository.findAll()
                .stream()
                .map(theme -> modelMapper.map(theme, ThemeResponseDTO.class))
                .toList();
    }

    @Override
    public ThemeResponseDTO getThemeById(UUID id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException("Thème non trouvé avec l'id : " + id));
        return modelMapper.map(theme, ThemeResponseDTO.class);
    }

    @Override
    public ThemeResponseDTO updateTheme(UUID id, ThemeUpdateDTO themeUpdateDTO) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException("Thème non trouvé avec l'id : " + id));

        if (themeUpdateDTO.getTitle() != null) {
            // Vérification de l'unicité du titre
            if (themeRepository.existsByTitle(themeUpdateDTO.getTitle())) {
                throw new DuplicateThemeTitleException(
                        "Un thème avec le titre \"" + themeUpdateDTO.getTitle() + "\" existe déjà."
                );
            }
            theme.setTitle(themeUpdateDTO.getTitle());
            System.out.println("Titre mis à jour : " + theme.getTitle());
        }
        if (themeUpdateDTO.getIsActive() != null) {
            theme.setActive(themeUpdateDTO.getIsActive());
            System.out.println("Statut actif mis à jour : " + theme.isActive());
        }

        Theme updatedTheme = themeRepository.save(theme);
        System.out.println("Thème sauvegardé : " + updatedTheme.isActive());
        return modelMapper.map(updatedTheme, ThemeResponseDTO.class);
    }

    @Override
    public void deleteTheme(UUID id) {
        try {
            themeRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ThemeNotFoundException("Thème non trouvé avec l'id : " + id);
        }
    }

    @Override
    public ThemeResponseDTO setActiveStatus(UUID id, boolean status) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException("Thème non trouvé avec l'id : " + id));
        theme.setActive(status);
        Theme updatedTheme = themeRepository.save(theme);
        return modelMapper.map(updatedTheme, ThemeResponseDTO.class);
    }
}
