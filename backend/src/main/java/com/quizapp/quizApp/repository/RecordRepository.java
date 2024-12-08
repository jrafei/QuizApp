package com.quizapp.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import com.quizapp.quizApp.model.beans.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
}
