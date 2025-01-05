package com.quizapp.quizApp.service.interfac;

import com.quizapp.quizApp.model.dto.CompletedRecordDTO;
import com.quizapp.quizApp.model.dto.creation.RecordCreateDTO;
import com.quizapp.quizApp.model.dto.creation.UserCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizRankingStatsDTO;
import com.quizapp.quizApp.model.dto.response.QuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.RecordResponseDTO;
import com.quizapp.quizApp.model.dto.response.ThemeStatsDTO;
import com.quizapp.quizApp.model.dto.UserQuizResultsDTO;
import com.quizapp.quizApp.model.beans.Record;
import com.quizapp.quizApp.model.dto.response.UserQuizStatsDTO;
import com.quizapp.quizApp.model.dto.response.UserThemeStatsDTO;
import com.quizapp.quizApp.model.dto.QuizLeaderboardDTO;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface RecordService {
    RecordResponseDTO createRecord(RecordCreateDTO user);
    int calculateScore(Record record);

    List<RecordResponseDTO> getAllRecords();

    List<UserQuizStatsDTO> getUserStatsByQuiz(UUID userId);

    List<UserThemeStatsDTO> getUserStatsByTheme(UUID userId);

    Record assignQuizToTrainee(UUID traineeId, UUID quizId);

    List<RecordResponseDTO> getPendingQuizzesForTraineeByEmail(String email);

    List<CompletedRecordDTO> getCompletedRecordsForUser(String email);

    UserQuizResultsDTO getUserResultsForQuiz(UUID userId, UUID quizId);

    List<QuizLeaderboardDTO> getQuizLeaderboard(UUID quizId);

    List<ThemeStatsDTO> getStatsOfAllThemes();

    List<QuizStatsDTO> getStatsOfQuizzesRelatedToTheme(@PathVariable UUID themeId);

    List<QuizRankingStatsDTO> getQuizRankings(@PathVariable UUID quizId);
}
