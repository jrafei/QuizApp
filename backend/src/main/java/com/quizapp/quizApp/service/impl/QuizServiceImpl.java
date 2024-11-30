package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.QuizNotFoundException;
import com.quizapp.quizApp.model.beans.Answer;
import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.repository.*;
import com.quizapp.quizApp.service.interfac.QuizService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ThemeRepository themeRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private void reorganizePositions(UUID themeId) {
        // Récupérer les quiz actifs triés par position
        List<Quiz> activeQuizzes = quizRepository.findByThemeIdAndIsActive(themeId, true)
                .stream()
                .sorted(Comparator.comparingInt(Quiz::getPosition))
                .toList();

        // Réindexer les positions
        for (int i = 0; i < activeQuizzes.size(); i++) {
            activeQuizzes.get(i).setPosition(i + 1);
        }

        // Sauvegarder les changements
        quizRepository.saveAll(activeQuizzes);
    }

    private int getMaxPosition(UUID themeId) {
        return quizRepository.findByThemeIdAndIsActive(themeId, true)
                .stream()
                .mapToInt(Quiz::getPosition)
                .max()
                .orElse(0);
    }

    @Override
    public QuizResponseDTO createQuiz(QuizCreateDTO quizCreateDTO) {
        // Vérifier si un quiz avec le même nom existe déjà dans le thème
        if (quizRepository.existsByNameAndThemeId(quizCreateDTO.getName(), quizCreateDTO.getThemeId())) {
            throw new IllegalArgumentException("Un quiz avec le nom '" + quizCreateDTO.getName() + "' existe déjà dans ce thème.");
        }

        // Mapper le DTO vers une entité Quiz
        Quiz quiz = modelMapper.map(quizCreateDTO, Quiz.class);

        // Récupérer le créateur et le thème
        quiz.setCreator(userRepository.findById(quizCreateDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Créateur non trouvé avec l'ID : " + quizCreateDTO.getCreatorId())));
        quiz.setTheme(themeRepository.findById(quizCreateDTO.getThemeId())
                .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'ID : " + quizCreateDTO.getThemeId())));

        // Définir la position uniquement si le quiz est actif
        quiz.setIsActive(false); // Par défaut, le quiz est inactif
        quiz.setPosition(null); // Position par défaut pour les quiz inactifs

        // Sauvegarder le quiz
        Quiz savedQuiz = quizRepository.save(quiz);

        // Mapper l'entité sauvegardée vers un QuizResponseDTO
        return modelMapper.map(savedQuiz, QuizResponseDTO.class);
    }

    @Override
    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
                .toList();
    }

    @Override
    public List<QuizResponseDTO> getQuizzesByCreator(UUID creatorId) {
        return quizRepository.findByCreatorId(creatorId)
                .stream()
                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
                .toList();
    }

    @Override
    public QuizResponseDTO updateQuiz(UUID id, QuizCreateDTO quizCreateDTO) {
        // Log : Afficher le DTO reçu
        System.out.println("Received QuizCreateDTO: " + quizCreateDTO);

        // Récupérer le quiz existant
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz non trouvé avec l'ID : " + id));

        // Log : Afficher l'état du quiz avant la mise à jour
        System.out.println("Quiz before update: " + quiz);

        // Modifier les champs du quiz
        if (quizCreateDTO.getName() != null) {
            quiz.setName(quizCreateDTO.getName());
        }
        if (quizCreateDTO.getPosition() != null) {
            quiz.setPosition(quizCreateDTO.getPosition());
        }

        // Log : Afficher les valeurs après modification
        System.out.println("Updated quiz: " + quiz);

        // Sauvegarder les modifications
        Quiz updatedQuiz = quizRepository.save(quiz);

        // Log : Afficher l'objet mis à jour
        System.out.println("Saved quiz: " + updatedQuiz);

        // Retourner le DTO de la réponse
        return modelMapper.map(updatedQuiz, QuizResponseDTO.class);
    }

    @Override
    public QuizResponseDTO setActiveStatus(UUID id, boolean isActive) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé avec l'ID : " + id));

        UUID themeId = quiz.getTheme().getId();

        if (isActive) {
            // Vérifier si toutes les questions actives du quiz sont valides
            if (!areAllActiveQuestionsValid(id)) {
                throw new IllegalArgumentException("Le quiz ne peut pas être activé car certaines questions actives ne sont pas valides.");
            }

            // Si la position actuelle est en conflit ou n'existe pas, attribuer une nouvelle position
            if (quiz.getPosition() == null || quizRepository.findByThemeIdAndIsActive(themeId, true)
                    .stream()
                    .anyMatch(q -> q.getPosition().equals(quiz.getPosition()))) {
                quiz.setPosition(getMaxPosition(themeId) + 1);
            }
        } else {
            // Désactiver le quiz
            quiz.setPosition(null);
            quiz.setIsActive(false);

            // Sauvegarder le quiz avant de réorganiser les positions
            quizRepository.save(quiz);

            // Réorganiser les positions des quiz actifs
            reorganizePositions(themeId);
        }

        quiz.setIsActive(isActive);
        Quiz updatedQuiz = quizRepository.save(quiz);
        return modelMapper.map(updatedQuiz, QuizResponseDTO.class);
    }


    @Override
    public void deleteQuiz(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé avec l'ID : " + id));

        UUID themeId = quiz.getTheme().getId();

        // Supprimer le quiz
        quizRepository.delete(quiz);

        // Réorganiser les positions des quiz actifs
        reorganizePositions(themeId);
    }

    @Override
    public List<QuizResponseDTO> getQuizzesByIsActive(boolean isActive) {
        return quizRepository.findByIsActive(isActive)
                .stream()
                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
                .toList();
    }

    @Override
    public List<QuizResponseDTO> getQuizzesByTheme(UUID themeId) {
        List<Quiz> quizzes = quizRepository.findByThemeId(themeId);
        return quizzes.stream()
                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
                .toList();
    }

    private boolean areAllActiveQuestionsValid(UUID quizId) {
        List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrue(quizId);

        // Vérifier que chaque question active a au moins 2 réponses actives et une seule vraie
        for (Question question : activeQuestions) {
            List<Answer> activeAnswers = answerRepository.findAllByQuestionIdAndIsActiveTrue(question.getId());

            // Vérifier qu'il y a au moins deux réponses actives
            if (activeAnswers.size() < 2) {
                return false;
            }

            // Vérifier qu'il y a une seule réponse vraie
            long correctAnswerCount = activeAnswers.stream().filter(Answer::getCorrect).count();
            if (correctAnswerCount != 1) {
                return false;
            }
        }

        return true;
    }

}
