package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.dto.QuestionDTO;
import com.quizapp.quizApp.service.interfac.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(
            @RequestParam(required = false) UUID quizId) {
        List<QuestionDTO> questions = (quizId != null)
                ? questionService.getAllQuestionsByQuiz(quizId)
                : questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO createdQuestion = questionService.createQuestion(questionDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable UUID id,
            @RequestBody QuestionDTO questionDTO) {
        QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateQuestion(@PathVariable UUID id) {
        questionService.activateQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateQuestion(@PathVariable UUID id) {
        questionService.deactivateQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
