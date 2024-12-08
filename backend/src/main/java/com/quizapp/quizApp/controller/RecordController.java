package com.quizapp.quizApp.controller;


import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quizapp.quizApp.service.interfac.RecordService;
import java.util.ArrayList;
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
}
