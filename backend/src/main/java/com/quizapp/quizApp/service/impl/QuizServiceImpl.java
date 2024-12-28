package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.QuizNotFoundException;
import com.quizapp.quizApp.model.beans.*;
import com.quizapp.quizApp.model.dto.creation.AnswerCreateDTO;
import com.quizapp.quizApp.model.dto.creation.QuestionCreateDTO;
import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.model.dto.update.QuizUpdateDTO;
import com.quizapp.quizApp.repository.*;
import com.quizapp.quizApp.service.interfac.QuizService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
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

        System.out.println("debut createQUiz");
        // Charger le créateur et le thème
//        User creator = userRepository.findById(quizCreateDTO.getCreatorId())
//                .orElseThrow(() -> new IllegalArgumentException("Créateur introuvable."));
        Theme theme = themeRepository.findById(quizCreateDTO.getThemeId())
                .orElseThrow(() -> new IllegalArgumentException("Thème introuvable."));


        System.out.println("debut modelMapper configuration");
        // // Configurer le TypeMap avec les règles de mapping spécifiques
        modelMapper.typeMap(QuizCreateDTO.class, Quiz.class).addMappings(mapper -> {
            //mapper.skip(Quiz::setCreator); // Ignorer le mapping par défaut
            mapper.skip(Quiz::setTheme);   // Ignorer le mapping par défaut
        });

        // Mapper le DTO vers une entité Quiz
        System.out.println("debut Mapper");
        Quiz quiz = modelMapper.map(quizCreateDTO, Quiz.class);

        System.out.println("fin Mapper");
//        quiz.setCreator(creator);
        quiz.setTheme(theme);

        // Quiz inactif à la création
        quiz.setIsActive(false); // Par défaut, le quiz est inactif
        quiz.setPosition(null); // Position par défaut pour les quiz inactifs


        // Initialiser les positions des questions
        if (quizCreateDTO.getQuestions() != null && !quizCreateDTO.getQuestions().isEmpty()) {
            int position = 1;
            for (Question question : quiz.getQuestions()) {
                question.setQuiz(quiz); // Associer la question au quiz
                question.setPosition(position++); // Définir la position de la question
                question.getAnswers().forEach(answer -> answer.setQuestion(question)); // Associer les réponses
            }
        }
        System.out.println("Save quiz ");

        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new ArrayList<>());
        }

        quizRepository.save(quiz);

        System.out.println("taille de la liste des questions : " + quiz.getNbQuestion());
        // Mapper l'entité sauvegardée vers un QuizResponseDTO
        return modelMapper.map(quiz, QuizResponseDTO.class);
    }

    @Override
    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
                .toList();
    }

//    @Override
//    public List<QuizResponseDTO> getQuizzesByCreator(UUID creatorId) {
//        return quizRepository.findByCreatorId(creatorId)
//                .stream()
//                .map(quiz -> modelMapper.map(quiz, QuizResponseDTO.class))
//                .toList();
//    }

    @Override
    @Transactional
    public QuizResponseDTO updateQuiz(UUID id, QuizUpdateDTO quizUpdateDTO) {
        // Log : Afficher le DTO reçu
        System.out.println("Received QuizCreateDTO: " + quizUpdateDTO);

        System.out.println("id de quiz = "+ id);
        // Récupérer le quiz existant
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz non trouvé avec l'ID : " + id));

        // Log : Afficher l'état du quiz avant la mise à jour
        System.out.println("Quiz before update: ");

        // Modifier les champs du quiz
        if (quizUpdateDTO.getName() != null) {
            quiz.setName(quizUpdateDTO.getName());
        }


        if (quizUpdateDTO.getPosition() != null) {
            quiz.setPosition(quizUpdateDTO.getPosition());
        }


        // Log : Afficher les valeurs après modification
        System.out.println("Updated quiz: " );

        // Sauvegarder les modifications
        Quiz updatedQuiz = quizRepository.save(quiz);

        // Log : Afficher l'objet mis à jour
        System.out.println("Saved quiz: ");

        // Retourner le DTO de la réponse
        return modelMapper.map(updatedQuiz, QuizResponseDTO.class);
    }

    /*
    private void updatePosition(Quiz quiz, QuizCreateDTO quizCreateDTO) {
        int newPosition = quizCreateDTO.getPosition();

        Theme theme = themeRepository.findById(quizCreateDTO.getThemeId())
                .orElseThrow(() -> new IllegalArgumentException("Thème associé au quiz est introuvable."));

        int nbQuiz = theme.getQuizzes().size();
        if (newPosition > nbQuiz){
            throw new RuntimeException("Position invalide : supérieur au nombre de quizs ");
        }

    }
    */


    @Override
    public QuizResponseDTO setActiveStatus(UUID id, Boolean isActive) {
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
    public List<QuizResponseDTO> getQuizzesByIsActive(Boolean isActive) {
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
