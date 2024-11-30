package com.quizapp.quizApp.model.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeUpdateDTO {
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères.")
    private String title;

    @JsonProperty("active")
    private Boolean isActive;
}
