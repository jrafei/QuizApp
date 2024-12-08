package com.quizapp.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import com.quizapp.quizApp.model.beans.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    // Méthode générée automatiquement par Spring Data JPA
    List<Record> findByTraineeIdAndQuizId(UUID traineeId, UUID quizId);


    // Trouver tous les records d'un utilisateur
    List<Record> findByTraineeId(UUID traineeId);
}
