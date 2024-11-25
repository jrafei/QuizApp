package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.QuizDTO;
import com.quizapp.quizApp.repository.ThemeRepository;
import com.quizapp.quizApp.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizs")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;



    @GetMapping
    public List<Quiz> getAllQuizs() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDTO quizDTO) {
        Quiz createdQuiz = quizService.createQuiz(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }
}
