package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "themes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_theme", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Le titre du thème est obligatoire.")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères.")
    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Quiz> quizzes; // Liste des Quiz associés
}
