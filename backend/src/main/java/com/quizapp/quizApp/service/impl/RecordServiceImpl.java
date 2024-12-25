package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.QuizNotFoundException;
import com.quizapp.quizApp.exception.UserNotFoundException;
import com.quizapp.quizApp.model.beans.*;
import com.quizapp.quizApp.model.beans.Record;
import com.quizapp.quizApp.model.dto.CompletedRecordDTO;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import com.quizapp.quizApp.repository.AnswerRepository;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.repository.RecordRepository;
import com.quizapp.quizApp.repository.UserRepository;
import com.quizapp.quizApp.service.interfac.RecordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

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
        record.setStatus(Record.RecordStatus.valueOf(request.getStatus()));

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

    public List<CompletedRecordDTO> getCompletedRecordsForUser(String email) {
        // Rechercher le stagiaire par email
        User trainee = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Trainee not found with email: " + email));

        // Récupérer les records terminés
        List<Record> completedRecords = recordRepository.findByTraineeIdAndStatus(trainee.getId(), Record.RecordStatus.COMPLETED);

        // Mapper les records vers des DTOs
        return completedRecords.stream()
                .map(record -> modelMapper.map(record, CompletedRecordDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserQuizStatsDTO> getUserStatsByQuiz(UUID userId) {
        // Vérification si l'utilisateur existe
        User trainee = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));


        // Utilisation de la méthode générée automatiquement
        List<Record> records = recordRepository.findByTraineeId(userId);


        // Grouper par quiz et calculer les scores et durées
        Map<String, List<Record>> recordsByQuiz = records.stream()
                .collect(Collectors.groupingBy(record -> record.getQuiz().getName()));

        // Transformer les résultats en DTO
        return recordsByQuiz.entrySet().stream().map(entry -> {
            String quizName = entry.getKey();
            List<Record> quizRecords = entry.getValue();

            int totalScore = quizRecords.stream().mapToInt(Record::getScore).sum();
            int totalDuration = quizRecords.stream().mapToInt(Record::getDuration).sum();

            return new UserQuizStatsDTO(quizName, totalScore, totalDuration, trainee.getEmail());
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserThemeStatsDTO> getUserStatsByTheme(UUID userId) {
        // Récupérer tous les records pour cet utilisateur
        List<Record> records = recordRepository.findByTraineeId(userId);

        // Grouper par thème et calculer les scores et durées
        Map<String, List<Record>> recordsByTheme = records.stream()
                .collect(Collectors.groupingBy(record -> record.getQuiz().getTheme().getTitle()));

        // Transformer les résultats en DTO
        return recordsByTheme.entrySet().stream().map(entry -> {
            String themeName = entry.getKey();
            List<Record> themeRecords = entry.getValue();

            int totalScore = themeRecords.stream().mapToInt(Record::getScore).sum();
            int totalDuration = themeRecords.stream().mapToInt(Record::getDuration).sum();

            return new UserThemeStatsDTO(themeName, totalScore, totalDuration);
        }).collect(Collectors.toList());
    }

    public Record assignQuizToTrainee(UUID traineeId, UUID quizId) {
        // Vérifiez que le stagiaire existe
        User trainee = userRepository.findById(traineeId)
                .orElseThrow(() -> new UserNotFoundException("Trainee not found with ID: " + traineeId));

        // Vérifiez que le rôle de l'utilisateur est TRAINEE
        if (!trainee.getRole().equals(User.Role.TRAINEE)) {
            throw new IllegalArgumentException("The user with ID " + traineeId + " is not a trainee.");
        }

        // Vérifiez que le quiz existe
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + quizId));

        // Vérifiez que le quiz est actif
        if (!quiz.getIsActive()) {
            throw new IllegalArgumentException("Cannot assign an inactive quiz.");
        }

        // Créez un nouvel enregistrement Record
        Record record = new Record();
        record.setTrainee(trainee);
        record.setQuiz(quiz);
        record.setStatus(Record.RecordStatus.PENDING);

        // Sauvegardez l'enregistrement
        Record savedRecord = recordRepository.save(record);

        // Envoyer un e-mail au stagiaire
        emailService.sendQuizAssignmentEmail(trainee.getEmail(), trainee.getFirstname(), quiz.getName());

        return savedRecord;
    }

    public List<RecordResponseDTO> getPendingQuizzesForTraineeByEmail(String email) {
        // Rechercher le stagiaire par email
        User trainee = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Trainee not found with email: " + email));

        // Rechercher les records avec un statut PENDING
        List<Record> records = recordRepository.findByTraineeIdAndStatus(trainee.getId(), Record.RecordStatus.PENDING);

        // Mapper les Records vers des DTOs
        return records.stream()
                .map(record -> modelMapper.map(record, RecordResponseDTO.class))
                .collect(Collectors.toList());
    }
}


