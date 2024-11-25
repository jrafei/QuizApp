package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
