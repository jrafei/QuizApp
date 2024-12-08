package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.model.beans.*;
import com.quizapp.quizApp.model.beans.Record;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.repository.AnswerRepository;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.repository.RecordRepository;
import com.quizapp.quizApp.repository.UserRepository;
import com.quizapp.quizApp.service.interfac.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;


    /**
     * Crée un record avec les informations fournies dans le DTO
     * @param request RecordCreateDTO contenant les données pour la création
     * @return RecordResponseDTO contenant les informations du record créé
     */
    @Override
    public RecordResponseDTO createRecord(RecordCreateDTO request){
        // Vérification de l'existence de l'utilisateur (trainee)
        User trainee = userRepository.findById(request.getTraineeId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getTraineeId()));

        // Vérification de l'existence du quiz
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + request.getQuizId()));

        // Créer un nouvel objet Record
        Record record = new Record();
        record.setTrainee(trainee);
        record.setQuiz(quiz);

        record.setDuration(request.getDuration());

        // Gérer les réponses choisies via RecordAnswer
        List<RecordAnswer> recordAnswers = new ArrayList<>();
        for (UUID answerId : request.getAnswerIds()) {
            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + answerId));
            RecordAnswer recordAnswer = new RecordAnswer();
            recordAnswer.setRecord(record);
            recordAnswer.setAnswer(answer);
            recordAnswers.add(recordAnswer);
        }
        record.setRecordAnswers(recordAnswers);

        // Calculer le score
        int score = calculateScore(record);
        record.setScore(score);

        // Sauvegarder le record
        Record savedRecord = recordRepository.save(record);

        // Retourner le DTO de réponse
        return new RecordResponseDTO(
                savedRecord.getId(),
                savedRecord.getScore(),
                savedRecord.getDuration(),
                savedRecord.getTrainee().getId(),
                savedRecord.getQuiz().getId()
        );
    }

    @Override
    public int calculateScore(Record record) {
        // Récupérer les réponses associées au Record
        List<RecordAnswer> recordAnswers = record.getRecordAnswers();

        // Calculer le score en comptant les bonnes réponses
        int score = 0;
        for (RecordAnswer recordAnswer : recordAnswers) {
            if (recordAnswer.getAnswer().isCorrect()) { // Vérifie si la réponse est correcte
                score++;
            }
        }

        return score;
    }

    @Override
    public List<RecordResponseDTO> getAllRecords() {
        // Récupérer tous les records depuis le repository
        List<Record> records = recordRepository.findAll();

        // Convertir les records en RecordResponseDTO
        return records.stream().map(record -> new RecordResponseDTO(
                record.getId(),
                record.getScore(),
                record.getDuration(),
                record.getTrainee().getId(),
                record.getQuiz().getId()
        )).toList();
    }



}
