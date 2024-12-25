package com.quizapp.quizApp.model.dto.creation;
import com.quizapp.quizApp.model.beans.Record;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordCreateDTO {
    private UUID traineeId;      // ID de l'utilisateur
    private UUID quizId;         // ID du quiz
    private List<UUID> answerIds; // Liste des IDs des réponses choisies
    private int score;           // Score obtenu
    private int duration;        // Durée du quiz

    @Pattern(regexp = "PENDING|COMPLETED", message = "Invalid status. Must be PENDING or COMPLETED.")
    private String status;       // Statut du record (PENDING ou COMPLETED)
}