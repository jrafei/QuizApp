package com.quizapp.quizApp.service;
import java.sql.Timestamp;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.beans.Theme;
import com.quizapp.quizApp.model.dto.QuizDTO;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private ThemeRepository themeRepository;

    @Override
    public Quiz createQuiz(QuizDTO quizDTO){

        Theme theme = themeRepository.findById(quizDTO.getThemeId())
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + quizDTO.getThemeId()));

        // Créer une nouvelle instance de Quiz et associer le thème
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setActive(quizDTO.isActive());
        quiz.setTheme(theme);// Associer le thème au quiz
        quiz.setCreationDate(new Timestamp(System.currentTimeMillis()) ); // Initialiser la date

        // ajouter le quiz à la liste des quizzes de thème
        //theme.getQuizzes().add(quiz);

        // Sauvegarder le thème pour propager la mise à jour bidirectionnelle
        themeRepository.save(theme);

        // Sauvegarder le quiz
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

}
