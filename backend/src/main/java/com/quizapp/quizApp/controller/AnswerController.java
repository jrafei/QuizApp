package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.dto.AnswerDTO;
import com.quizapp.quizApp.service.interfac.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<AnswerDTO>> getAllAnswers(
            @RequestParam(required = false) UUID questionId) {
        if (questionId != null) {
            return ResponseEntity.ok(answerService.getAllAnswersByQuestion(questionId));
        }
        return ResponseEntity.ok(answerService.getAllAnswers());
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> createAnswer(@RequestBody AnswerDTO answerDTO) {
        System.out.println("Received AnswerDTO: " + answerDTO);
        return ResponseEntity.ok(answerService.createAnswer(answerDTO));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateAnswer(@PathVariable UUID id) {
        answerService.activateAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateAnswer(@PathVariable UUID id) {
        answerService.deactivateAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(
            @PathVariable UUID id,
            @RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.updateAnswer(id, answerDTO));
    }

    @PatchMapping("/{id}/set-correct")
    public ResponseEntity<Void> setCorrectAnswer(@PathVariable UUID id) {
        answerService.setCorrectAnswer(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
