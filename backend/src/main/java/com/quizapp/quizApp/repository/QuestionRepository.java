package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByQuizIdAndIsActiveTrueOrderByPosition(UUID quizId);
    List<Question> findAllByQuizIdOrderByPosition(UUID quizId);
}
