package com.quizapp.quizApp.controller;


import com.quizapp.quizApp.model.dto.AssignQuizRequestDTO;
import com.quizapp.quizApp.model.dto.QuizLeaderboardDTO;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.model.dto.response.ThemeStatsDTO;
import com.quizapp.quizApp.model.dto.response.TraineeStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.QuizRankingStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserQuizResultsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import com.quizapp.quizApp.model.dto.response.QuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeResultsDTO;
import com.quizapp.quizApp.model.dto.update.RecordUpdateDTO;
import com.quizapp.quizApp.model.dto.CompletedRecordDTO;
import com.quizapp.quizApp.model.dto.QuestionDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.quizapp.quizApp.service.interfac.RecordService;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.beans.Record;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/pending-quizzes")
    public ResponseEntity<List<QuizResponseDTO>> getPendingQuizzesByRecord() {
        // Récupérer l'utilisateur connecté depuis le contexte de sécurité
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Appeler le service pour obtenir les quizzes assignés à cet utilisateur
        List<RecordResponseDTO> pendingRecords = recordService.getPendingQuizzesForTraineeByEmail(email);

        List<UUID> recordIds = pendingRecords.stream()
                                            .map(RecordResponseDTO::getId)
                                            .collect(Collectors.toList());     
                                            
        List<QuizResponseDTO> quizzes = recordService.findQuizzesByRecordIds(recordIds);
        return ResponseEntity.ok(quizzes);
    }  


    // Endpoint pour récupérer les parcours complétés pour l'utilisateur connecté
    @GetMapping("/completed")
    public ResponseEntity<List<CompletedRecordDTO>> getCompletedRecords() {
        // Récupérer l'utilisateur connecté
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Appeler le service pour récupérer les records complétés
        List<CompletedRecordDTO> completedRecords = recordService.getCompletedRecordsForUser(email);

        return ResponseEntity.ok(completedRecords);
    }

    // Par quiz 
    @GetMapping("/quizs/{quizId}/leaderboard")
    public ResponseEntity<List<QuizLeaderboardDTO>> getQuizLeaderboard(@PathVariable UUID quizId) {
        List<QuizLeaderboardDTO> leaderboard = recordService.getQuizLeaderboard(quizId);
        return ResponseEntity.ok(leaderboard);
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

    @PatchMapping("/{id}")
    public ResponseEntity<RecordResponseDTO> updateRecord(
            @PathVariable UUID id,
            @RequestBody RecordUpdateDTO recordUpdateDTO) {
        RecordResponseDTO updatedRecord = recordService.updateRecord(id, recordUpdateDTO);
        return ResponseEntity.ok(updatedRecord);
    }

    // ------------------- STATISTIQUES -------------------

    // Endpoint pour récupérer liste des thèmes avec score et durée exec
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/themes/all")
    public ResponseEntity<List<ThemeStatsDTO>> getStatsOfAllThemes() { 
        List<ThemeStatsDTO> themes = recordService.getStatsOfAllThemes();
        return ResponseEntity.ok(themes);
    }
   
    // Endpoint pour récupérer liste des quiz associé à un thème avec nom, nb questions
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/themes/byquiz/{themeId}")
    public ResponseEntity<List<QuizStatsDTO>> getStatsOfQuizzesRelatedToTheme(@PathVariable UUID themeId) {
        System.out.println("Requête reçue avec theme ID" + themeId);
        List<QuizStatsDTO> quizzes = recordService.getStatsOfQuizzesRelatedToTheme(themeId);
        return ResponseEntity.ok(quizzes);
    }

    // Endpoint pour récupérer classement de stagiaires par quiz
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/themes/rankings/{quizId}")
    public ResponseEntity<List<QuizRankingStatsDTO>> getQuizRankings(@PathVariable UUID quizId) {
        List<QuizRankingStatsDTO> ranking = recordService.getQuizRankings(quizId);
        return ResponseEntity.ok(ranking);
    }

    // Endpoint pour récupérer la liste de tous les stagiaires qui ont fait au moins un quiz (présence dans record)   
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/trainees/all")
    public ResponseEntity<List<TraineeStatsDTO>> getRecordedTrainees() {
        List<TraineeStatsDTO> trainees = recordService.getRecordedTrainees();
        return ResponseEntity.ok(trainees);
    }

    // Endpoint pour récuperer tous les thèmes, note et durée moyenne d'un stagiaire
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/trainees/{userId}/themes")
    public ResponseEntity<List<UserThemeStatsDTO>> getUserStatsByTheme(
            @PathVariable UUID userId) {
        List<UserThemeStatsDTO> stats = recordService.getUserStatsByTheme(userId);
        return ResponseEntity.ok(stats);
    }

    // Endpoint pour récupérer résultats d'un thème 
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/trainees/{userId}/themes/{themeId}")
    public ResponseEntity<UserThemeResultsDTO> getUserResultsForTheme(
            @PathVariable UUID userId,
            @PathVariable UUID themeId) {
        UserThemeResultsDTO results = recordService.getUserResultsForTheme(userId, themeId);
        return ResponseEntity.ok(results);
    }

    // Endpoint pour récuperer tous les questionnaires, note et durée d'un stagiaire 
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/trainees/{userId}/quizs")
    public ResponseEntity<List<UserQuizStatsDTO>> getUserStatsByQuiz(
            @PathVariable UUID userId) {
        List<UserQuizStatsDTO> stats = recordService.getUserStatsByQuiz(userId);
        return ResponseEntity.ok(stats);
    }

    // Endpoint pour récupérer résultats d'un quiz
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/stats/trainees/{userId}/quizs/{quizId}")
    public ResponseEntity<UserQuizResultsDTO> getUserResultsForQuiz(
            @PathVariable UUID userId,
            @PathVariable UUID quizId) {
        UserQuizResultsDTO results = recordService.getUserResultsForQuiz(userId, quizId);
        return ResponseEntity.ok(results);
    }

}