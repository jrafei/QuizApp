package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_answer", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Le libellé de la réponse est obligatoire.")
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "is_correct", nullable = false)
    private Boolean correct = false; // Indique si cette réponse est correcte

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false; // Par défaut : inactif

    @Column(name = "position")
    private Integer position = null; // Par défaut, pas d'ordre défini

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Question question; // Association à une Question parent

    /**
     * Détermine si une réponse peut être activée.
     */
    public boolean canBeActivated() {
        return this.position != null && this.question != null;
    }

    /**
     * Active la réponse en validant les conditions requises.
     */
    public void activate() {
        if (canBeActivated()) {
            this.isActive = true;
        } else {
            throw new IllegalStateException("Impossible d'activer la réponse : position ou question manquante.");
        }
    }

    /**
     * Désactive la réponse et réinitialise la position.
     */
    public void deactivate() {
        this.isActive = false;
        this.position = null; // Réinitialise la position si désactivée
    }
}
