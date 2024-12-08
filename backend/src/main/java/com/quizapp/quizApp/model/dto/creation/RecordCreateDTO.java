package com.quizapp.quizApp.model.dto.creation;
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
    //private int score;           // Score obtenu
    private int duration;        // Durée du quiz


}