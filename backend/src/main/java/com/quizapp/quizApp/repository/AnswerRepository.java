package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    /**
     * Récupère toutes les réponses actives d'une question donnée, triées par position.
     */
    List<Answer> findAllByQuestionIdAndIsActiveTrueOrderByPosition(UUID questionId);

    /**
     * Récupère toutes les réponses d'une question, peu importe leur statut.
     */
    List<Answer> findAllByQuestionIdOrderByPosition(UUID questionId);

    boolean existsByLabelAndQuestionId(String label, UUID questionId);
    boolean existsByLabelAndQuestionIdAndIdNot(String label, UUID questionId, UUID id);

}
