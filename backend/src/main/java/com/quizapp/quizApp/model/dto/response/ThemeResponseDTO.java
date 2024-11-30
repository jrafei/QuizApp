package com.quizapp.quizApp.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ThemeResponseDTO {
    private String id;
    private String title;
    private Boolean isActive;
}
