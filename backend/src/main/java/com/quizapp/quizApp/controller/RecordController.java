package com.quizapp.quizApp.controller;


import com.quizapp.quizApp.model.dto.AssignQuizRequestDTO;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;

import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.quizapp.quizApp.service.interfac.RecordService;
import com.quizapp.quizApp.model.beans.Record;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;


    @PostMapping
    public ResponseEntity<RecordResponseDTO> createRecord(@RequestBody RecordCreateDTO request) {
        // Appeler le service pour créer le Record
        RecordResponseDTO record = recordService.createRecord(request);

        // Retourner le Record créé
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<RecordResponseDTO>> getAllRecords() {
        List<RecordResponseDTO> records = recordService.getAllRecords();

        return ResponseEntity.ok(records);
    }


    // les resultats (note et durée) d'un stagiaire par questionnaire
    @GetMapping("/{userId}/stats/quizs")
    public ResponseEntity<List<UserQuizStatsDTO>> getUserStatsByQuiz(
            @PathVariable UUID userId) {
        List<UserQuizStatsDTO> stats = recordService.getUserStatsByQuiz(userId);
        return ResponseEntity.ok(stats);
    }


    // les resultats (note et durée) d'un stagiaire par themes
    @GetMapping("/{userId}/stats/themes")
    public ResponseEntity<List<UserThemeStatsDTO>> getUserStatsByTheme(
            @PathVariable UUID userId) {
        List<UserThemeStatsDTO> stats = recordService.getUserStatsByTheme(userId);
        return ResponseEntity.ok(stats);
    }

    // Endpoint pour assigner un quiz à un stagiaire
    @PostMapping("/assign")
    public ResponseEntity<String> assignQuiz(@RequestBody AssignQuizRequestDTO request) {
        recordService.assignQuizToTrainee(request.getTraineeId(), request.getQuizId());
        return ResponseEntity.ok("Quiz successfully assigned to the trainee.");
    }

    // Endpoint pour récupérer les quizzes assignés au stagiaire
    @GetMapping("/pending")
    public ResponseEntity<List<RecordResponseDTO>> getPendingQuizzes() {
        // Récupérer l'utilisateur connecté depuis le contexte de sécurité
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Appeler le service pour obtenir les quizzes assignés à cet utilisateur
        List<RecordResponseDTO> pendingRecords = recordService.getPendingQuizzesForTraineeByEmail(email);

        return ResponseEntity.ok(pendingRecords);
    }


}




