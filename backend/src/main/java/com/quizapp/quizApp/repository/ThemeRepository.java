package com.quizapp.quizApp.repository;

import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.model.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

}
