package com.quizapp.quizApp.model.dto.creation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeCreateDTO {
    @NotBlank(message = "Le titre du thème est obligatoire.")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères.")
    private String title;
}
