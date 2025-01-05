package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.QuizNotFoundException;
import com.quizapp.quizApp.exception.UserNotFoundException;
import com.quizapp.quizApp.model.beans.*;
import com.quizapp.quizApp.model.beans.Record;
import com.quizapp.quizApp.model.dto.CompletedRecordDTO;
import com.quizapp.quizApp.model.dto.QuizLeaderboardDTO;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizRankingStatsDTO;
import com.quizapp.quizApp.model.dto.response.QuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.model.dto.response.ThemeStatsDTO;
import com.quizapp.quizApp.model.dto.response.TraineeStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserQuizResultsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeResultsDTO;
import com.quizapp.quizApp.repository.AnswerRepository;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.repository.RecordRepository;
import com.quizapp.quizApp.repository.ThemeRepository;
import com.quizapp.quizApp.repository.UserRepository;
import com.quizapp.quizApp.service.interfac.RecordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

        private final RecordRepository recordRepository;
        private final UserRepository userRepository;
        private final QuizRepository quizRepository;
        private final ThemeRepository themeRepository;
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
        public UserQuizResultsDTO getUserResultsForQuiz(UUID userId, UUID quizId) {
                // Vérifiez si l'utilisateur existe
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

                // Vérifiez si le quiz existe
                Quiz quiz = quizRepository.findById(quizId)
                        .orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + quizId));

                // Récupérez les records pour l'utilisateur et le quiz
                List<Record> records = recordRepository.findByTraineeIdAndQuizId(userId, quizId);

                if (records.isEmpty()) {
                        throw new IllegalArgumentException("No records found for this user and quiz.");
                }

                // Calculer les statistiques
                double averageScore = records.stream().mapToInt(Record::getScore).average().orElse(0);
                int bestScore = records.stream().mapToInt(Record::getScore).max().orElse(0);
                int worstScore = records.stream().mapToInt(Record::getScore).min().orElse(0);

                return new UserQuizResultsDTO(quiz.getName(), averageScore, bestScore, worstScore);
        }

        @Override
        public List<QuizLeaderboardDTO> getQuizLeaderboard(UUID quizId) {
                // Vérifiez si le quiz existe
                Quiz quiz = quizRepository.findById(quizId)
                        .orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + quizId));

                // Récupérez tous les records pour ce quiz avec le statut COMPLETED
                List<Record> completedRecords = recordRepository.findByQuizIdAndStatus(quizId, Record.RecordStatus.COMPLETED);

                if (completedRecords.isEmpty()) {
                        throw new IllegalArgumentException("No completed records found for this quiz.");
                }

                // Groupez les records par utilisateur
                Map<User, List<Record>> recordsByUser = completedRecords.stream()
                        .collect(Collectors.groupingBy(Record::getTrainee));

                // Pour chaque utilisateur, sélectionnez la meilleure tentative et calculez le score pondéré
                List<QuizLeaderboardDTO> leaderboard = recordsByUser.entrySet().stream()
                        .map(entry -> {
                                User user = entry.getKey();
                                List<Record> userRecords = entry.getValue();

                                // Trouvez la meilleure tentative (meilleur score, durée minimale si égalité)
                                Record bestAttempt = userRecords.stream()
                                        .max(Comparator.comparingInt(Record::getScore)
                                                .thenComparingInt(Record::getDuration))
                                        .orElseThrow();

                                int bestScore = bestAttempt.getScore();
                                double averageDuration = userRecords.stream().mapToInt(Record::getDuration).average().orElse(0);
                                int attempts = userRecords.size();

                                // Calculez le score pondéré
                                double weightedScore = (bestScore * 1) - (averageDuration * 0.01); // Poids : Score=1, Temps=0.01

                                return new QuizLeaderboardDTO(
                                        user.getId(),
                                        user.getFirstname() + " " + user.getLastname(),
                                        bestScore,
                                        averageDuration,
                                        attempts,
                                        0, // Le rang sera calculé après
                                        weightedScore // Ajoutez le score pondéré
                                );
                        })
                        .collect(Collectors.toList());

                // Triez le classement par score pondéré
                leaderboard.sort(Comparator
                        .comparingDouble(QuizLeaderboardDTO::getWeightedScore).reversed()); // Score pondéré décroissant

                // Assignez les rangs
                for (int i = 0; i < leaderboard.size(); i++) {
                        leaderboard.get(i).setRank(i + 1);
                }

        return leaderboard;
        }

        @Override
        public List<UserQuizStatsDTO> getUserStatsByQuiz(UUID userId) {
                User trainee = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

                List<Record> records = recordRepository.findByTraineeId(userId);

                Map<UUID, List<Record>> recordsByQuiz = records.stream()
                        .collect(Collectors.groupingBy(record -> record.getQuiz().getId()));

                return recordsByQuiz.entrySet().stream().map(entry -> {
                        UUID quizId = entry.getKey();

                        String quizName = entry.getValue().stream()
                                        .findFirst()
                                        .map(record -> record.getQuiz().getName()) 
                                        .orElse("Unknown");

                        List<Record> quizRecords = entry.getValue();

                        int totalScore = quizRecords.stream().mapToInt(Record::getScore).sum();
                        int totalDuration = quizRecords.stream().mapToInt(Record::getDuration).sum();

                        return new UserQuizStatsDTO(quizId, quizName, totalScore, totalDuration, trainee.getEmail());
                }).collect(Collectors.toList());
        }

        @Override
        public List<UserThemeStatsDTO> getUserStatsByTheme(UUID userId) {
                List<Record> records = recordRepository.findByTraineeId(userId);

                Map<UUID, List<Record>> recordsByTheme = records.stream()
                        .collect(Collectors.groupingBy(record -> record.getQuiz().getTheme().getId()));

                return recordsByTheme.entrySet().stream().map(entry -> {
                        UUID themeId = entry.getKey();
                        String themeName = entry.getValue().stream()
                                        .findFirst()
                                        .map(record -> record.getQuiz().getTheme().getTitle()) 
                                        .orElse("Unknown");

                        List<Record> themeRecords = entry.getValue();

                        int totalScore = themeRecords.stream().mapToInt(Record::getScore).sum();
                        int totalDuration = themeRecords.stream().mapToInt(Record::getDuration).sum();

                        return new UserThemeStatsDTO(themeId, themeName, totalScore, totalDuration);
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

        @Override
        public List<ThemeStatsDTO> getStatsOfAllThemes() {
                List<Theme> themeList = themeRepository.findAll(); 
                List<ThemeStatsDTO> themeStatsList = new ArrayList<>(); 
                Long totalRecordsCount = recordRepository.count(); 
                
                for (Theme theme : themeList) {
                        List<UUID> quizIds = quizRepository.findByThemeId(theme.getId())
                                                        .stream()
                                                        .map(Quiz::getId)
                                                        .toList();
                
                        int recordCountForTheme = quizIds.isEmpty() ? 0 : recordRepository.countByQuizIdIn(quizIds);
                        Double percentage = totalRecordsCount > 0 ? (recordCountForTheme / (double) totalRecordsCount) * 100 : 0.0;
                        Double frequency = Math.round(percentage * 100.0) / 100.0;
                        
                        ThemeStatsDTO themeStatsDTO = new ThemeStatsDTO(
                                theme.getId(),
                                theme.getTitle(),
                                frequency
                        );
                        themeStatsList.add(themeStatsDTO);
                }
                themeStatsList.sort((a, b) -> Double.compare(b.getFrequency(), a.getFrequency()));

                return themeStatsList;
        }
      
        @Override
        public List<QuizStatsDTO> getStatsOfQuizzesRelatedToTheme(UUID themeId) {
                
                if (themeId == null) {
                        throw new IllegalArgumentException("Theme ID cannot be null");
                }
                
                List<QuizStatsDTO> quizzesStatsList = new ArrayList<>();
                List<Quiz> quizzes = quizRepository.findByThemeId(themeId);
                
                for (Quiz quiz : quizzes) {
                        UUID quizId = quiz.getId();
                        String quizName = quiz.getName();
                        int questionCount = quiz.getQuestions().size();

                        List<Record> records = recordRepository.findByQuizId(quizId);
                        
                        if (!records.isEmpty()) {
                                double averageScore = records.stream()
                                                        .mapToDouble(Record::getScore)
                                                        .average()
                                                        .orElse(0.0);
                    
                                double averageDuration = records.stream()
                                                        .mapToDouble(Record::getDuration)
                                                        .average()
                                                        .orElse(0.0);
                    
                                QuizStatsDTO quizStatsDTO = new QuizStatsDTO(
                                    quizId,
                                    quizName,
                                    questionCount,
                                    averageScore,
                                    averageDuration
                                );

                                quizzesStatsList.add(quizStatsDTO);  
                        }
                }
                quizzesStatsList.sort((a, b) -> Double.compare(b.getMeanscore(), a.getMeanscore()));
                return quizzesStatsList;
        }
        
        @Override
        public List<QuizRankingStatsDTO> getQuizRankings(UUID quizId) {
                if (quizId == null) {
                        throw new IllegalArgumentException("Quiz ID cannot be null");
                }

                List<Record> records = recordRepository.findByQuizId(quizId);
                
                Map<UUID, QuizRankingStatsDTO> bestScoresMap = new HashMap<>(); // Meilleur score par trainee
                
                Map<UUID, Double> averageScoresMap = records.stream()
                                                        .collect(Collectors.groupingBy(
                                                                record -> record.getTrainee().getId(),
                                                                Collectors.averagingDouble(Record::getScore)
                ));

                Map<UUID, Double> averageDurationsMap = records.stream()
                                                        .collect(Collectors.groupingBy(
                                                                record -> record.getTrainee().getId(),
                                                                Collectors.averagingDouble(Record::getDuration)
                ));

                for (Record record : records) {
                        UUID traineeId = record.getTrainee().getId();
                        User trainee = userRepository.findById(traineeId)
                                                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                        

                        Double averageScore = averageScoresMap.get(traineeId);
                        Double averageDuration = averageDurationsMap.get(traineeId);

                        if (!bestScoresMap.containsKey(traineeId) || bestScoresMap.get(traineeId).getScore() < record.getScore()) {
                                QuizRankingStatsDTO rankingStatsDTO = new QuizRankingStatsDTO(
                                        trainee.getFirstname(),
                                        trainee.getLastname(), 
                                        record.getScore(),
                                        record.getDuration(),
                                        averageScore,
                                        averageDuration
                                );
                                bestScoresMap.put(traineeId, rankingStatsDTO); // put(key, value)
                        }
                }
                List<QuizRankingStatsDTO> rankingList = new ArrayList<>(bestScoresMap.values());
                rankingList.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

                return rankingList;
        }

        @Override
        public List<TraineeStatsDTO> getRecordedTrainees() {

                List<TraineeStatsDTO> traineeStatsList = new ArrayList<>();

                List<Record> records = recordRepository.findAll();
                List<User> trainees = records.stream()
                                                .map(Record::getTrainee)
                                                .distinct()
                                                .collect(Collectors.toList()); 

                for (User trainee : trainees) {
                        TraineeStatsDTO traineeStatsDTO = new TraineeStatsDTO(
                                trainee.getId(),
                                trainee.getFirstname(),
                                trainee.getLastname()
                        );
                        traineeStatsList.add(traineeStatsDTO);
                }

                return traineeStatsList;
        }

        @Override
        public UserThemeResultsDTO getUserResultsForTheme(UUID userId, UUID themeId) {
                // Vérifiez si l'utilisateur existe
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

                // Vérifiez si le quiz existe
                Theme theme = themeRepository.findById(themeId)
                        .orElseThrow(() -> new QuizNotFoundException("Theme not found with ID: " + themeId));

                // Récupérez les records pour l'utilisateur
                List<Record> userrecords = recordRepository.findByTraineeId(userId);
                List<Record> records = new ArrayList<>();

                // Dans les records, on ne garde que les quizs associé au themeId
                for (Record userrecord : userrecords){
                        if (userrecord.getQuiz().getTheme().getId().equals(themeId)) {
                                records.add(userrecord);
                        }
                }

                if (records.isEmpty()) {
                        throw new IllegalArgumentException("No records found for this user and theme.");
                }

                // Calculer les statistiques
                double averageScore = records.stream().mapToInt(Record::getScore).average().orElse(0);
                int bestScore = records.stream().mapToInt(Record::getScore).max().orElse(0);
                int worstScore = records.stream().mapToInt(Record::getScore).min().orElse(0);

                return new UserThemeResultsDTO(theme.getTitle(), averageScore, bestScore, worstScore);
        }
}