package com.quizapp.quizApp.controller;


import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;

import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quizapp.quizApp.service.interfac.RecordService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;


    @PostMapping
    public ResponseEntity<RecordResponseDTO> createRecord(@RequestBody RecordCreateDTO request){
        // Appeler le service pour créer le Record
        RecordResponseDTO record = recordService.createRecord(request);

        // Retourner le Record créé
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<RecordResponseDTO>> getAllRecords(){
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



}




