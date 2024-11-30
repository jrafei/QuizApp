package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.creation.ThemeCreateDTO;
import com.quizapp.quizApp.model.dto.response.ThemeResponseDTO;
import com.quizapp.quizApp.model.dto.update.ThemeUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ThemeService {

    /**
     * Crée un nouveau thème.
     *
     * @param themeCreateDTO DTO contenant les informations du thème à créer.
     * @return DTO du thème créé.
     */
    ThemeResponseDTO createTheme(ThemeCreateDTO themeCreateDTO);

    /**
     * Récupère tous les thèmes.
     *
     * @return Liste de DTOs des thèmes existants.
     */
    List<ThemeResponseDTO> getAllThemes();

    /**
     * Récupère un thème par son ID.
     *
     * @param id ID du thème à récupérer.
     * @return DTO du thème trouvé.
     */
    ThemeResponseDTO getThemeById(UUID id);

    /**
     * Met à jour un thème partiellement.
     *
     * @param id ID du thème à mettre à jour.
     * @param themeUpdateDTO DTO contenant les champs à mettre à jour.
     * @return DTO du thème mis à jour.
     */
    ThemeResponseDTO updateTheme(UUID id, ThemeUpdateDTO themeUpdateDTO);

    /**
     * Supprime un thème par son ID.
     *
     * @param id ID du thème à supprimer.
     */
    void deleteTheme(UUID id);

    /**
     * Définit l'état d'activation d'un thème.
     *
     * @param id     ID du thème à modifier.
     * @param status Nouveau statut d'activation.
     * @return DTO du thème mis à jour.
     */
    ThemeResponseDTO setActiveStatus(UUID id, boolean status);
}
