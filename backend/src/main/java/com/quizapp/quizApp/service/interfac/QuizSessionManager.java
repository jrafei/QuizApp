package com.quizapp.quizApp.service.interfac;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class QuizSessionManager {

    // clé : id de quiz, valeur : liste des trainees travaillant sur le quiz
    private final Map<UUID, Set<UUID>> activeQuizSessions = new ConcurrentHashMap<>();


    /*  < A APPELER LORSQUE UN TRAINEE LANCE UN QUIZ >
    Met à jour le dictionnaire en ajoutant un trainee
    ConcurrentHashMap : Crée un ensemble thread-safe. Ceci est important car plusieurs threads peuvent accéder simultanément à la carte activeQuizSessions ou la modifier.
Cela garantit qu'aucune condition de concurrence ou incohérence de données ne se produit lorsque plusieurs stagiaires travaillent sur le même questionnaire.
     */
    public void startQuizSession(UUID quizId, UUID traineeId) {
        activeQuizSessions.computeIfAbsent(quizId, k -> ConcurrentHashMap.newKeySet()).add(traineeId);
    }




    // < A APPELER LORSQUE UN TRAINEE QUITTE UN QUIZ >
    public void endQuizSession(UUID quizId, UUID traineeId) {
        Set<UUID> trainees = activeQuizSessions.get(quizId);
        if (trainees != null) {
            trainees.remove(traineeId);
            if (trainees.isEmpty()) {
                activeQuizSessions.remove(quizId);
            }
        }
    }

    public boolean isTraineeWorkingOnQuiz(UUID quizId) {
        Set<UUID> trainees = activeQuizSessions.get(quizId);
        return trainees != null && !trainees.isEmpty();
    }

}
