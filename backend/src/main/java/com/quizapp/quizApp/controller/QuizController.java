package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.model.dto.update.QuizUpdateDTO;
import com.quizapp.quizApp.service.impl.QuizSessionManager;
import com.quizapp.quizApp.service.interfac.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quizzes")
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizSessionManager quizSessionManagerService;

    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizCreateDTO quizCreateDTO) {
        QuizResponseDTO createdQuiz = quizService.createQuiz(quizCreateDTO);
        return ResponseEntity.status(201).body(createdQuiz); // 201 Created
    }

    @PostMapping("/{idQuiz}")
    public ResponseEntity<QuizResponseDTO> createNewVersion(@PathVariable UUID idQuiz) {
        QuizResponseDTO createdQuiz = quizService.createNewVersion(idQuiz);
        return ResponseEntity.status(201).body(createdQuiz); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        List<QuizResponseDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes); // 200 OK
    }


    @GetMapping("/{idVersion}")
    public ResponseEntity<QuizResponseDTO> getLatestQuiz(@PathVariable UUID idVersion) {
        QuizResponseDTO quiz= quizService.getLatestQuiz(idVersion);
        return ResponseEntity.ok(quiz); // 200 OK
    }

    @GetMapping("/status")
    public ResponseEntity<List<QuizResponseDTO>> getQuizzesByIsActive(@RequestParam Boolean isActive) {
        List<QuizResponseDTO> quizzes = quizService.getQuizzesByIsActive(isActive);
        return ResponseEntity.ok(quizzes); // 200 OK
    }

    @GetMapping("/users/{creatorId}")
    public ResponseEntity<List<QuizResponseDTO>> getQuizzesByCreator(@PathVariable UUID creatorId) {
        List<QuizResponseDTO> quizzes = quizService.getQuizzesByCreator(creatorId);
        return ResponseEntity.ok(quizzes); // 200 OK
    }

    @GetMapping("/theme/{themeId}")
    public ResponseEntity<List<QuizResponseDTO>> getQuizzesByTheme(@PathVariable UUID themeId) {
        List<QuizResponseDTO> quizzes = quizService.getQuizzesByTheme(themeId);
        return ResponseEntity.ok(quizzes); // 200 OK
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> updateQuiz(@PathVariable UUID id, @RequestBody QuizUpdateDTO quizCreateDTO) {
        QuizResponseDTO updatedQuiz = quizService.updateQuiz(id, quizCreateDTO);
        return ResponseEntity.ok(updatedQuiz); // 200 OK
    }

    @PostMapping("/startquiz")
    public void startQuizSession(@RequestParam UUID quizId, @RequestParam UUID traineeId) {
        quizSessionManagerService.startQuizSession(quizId, traineeId);
    }

    @PostMapping("/endquiz")
    public void endQuizSession(@RequestParam UUID quizId, @RequestParam UUID traineeId) {
        quizSessionManagerService.endQuizSession(quizId, traineeId);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<QuizResponseDTO> activateQuiz(@PathVariable UUID id) {
        QuizResponseDTO updatedQuiz = quizService.setActiveStatus(id, true);
        return ResponseEntity.ok(updatedQuiz);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<QuizResponseDTO> deactivateQuiz(@PathVariable UUID id) {
        QuizResponseDTO updatedQuiz = quizService.setActiveStatus(id, false);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable UUID id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



}
