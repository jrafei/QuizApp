package com.quizapp.quizApp.model.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_question", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Le libellé de la question est obligatoire.")
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false; // Valeur par défaut : inactif à la création

    @Column(name = "position")
    private Integer position = null; // Par défaut, aucune position définie

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz; // Association au Quiz parent

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Answer> answers; // Liste de réponses associées

    /**
     * Détermine si une question peut être activée.
     * La position doit être définie et le quiz parent doit exister.
     */
    public boolean canBeActivated() {
        return this.position != null && this.quiz != null;
    }

    /**
     * Active la question en validant les conditions requises.
     */
    public void activate() {
        if (canBeActivated()) {
            this.isActive = true;
        } else {
            throw new IllegalStateException("Impossible d'activer la question : position ou quiz manquant.");
        }
    }

    /**
     * Désactive la question et réinitialise la position.
     */
    public void deactivate() {
        this.isActive = false;
        this.position = null; // Réinitialise la position si désactivée
    }
}
