package com.quizapp.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import com.quizapp.quizApp.model.beans.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    // Méthode générée automatiquement par Spring Data JPA
    List<Record> findByTraineeIdAndQuizId(UUID traineeId, UUID quizId);

    List<Record> findByTraineeId(UUID traineeId);

    List<Record> findByTraineeIdAndStatus(UUID traineeId, Record.RecordStatus recordStatus);

    List<Record> findByQuizId(UUID quizId);

    List<Record> findByQuizIdAndStatus(UUID quizId, Record.RecordStatus status);

    int countByQuizIdIn(List<UUID> quizIds);
}
