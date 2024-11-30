package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.exception.QuizNotFoundException;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import com.quizapp.quizApp.model.dto.response.QuizResponseDTO;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.repository.UserRepository;
import com.quizapp.quizApp.repository.ThemeRepository;
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
        quiz.setActive(false); // Par défaut, le quiz est inactif
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

//    @Override
//    public QuizResponseDTO updateQuiz(UUID id, QuizCreateDTO quizCreateDTO) {
//        //debug
//        System.out.println("QuizCreateDTO received: " + quizCreateDTO);
//        // Vérification de la position
//        if (quizCreateDTO.getPosition() != null) {
//            System.out.println("New position: " + quizCreateDTO.getPosition());
//        }
//
//        Quiz quiz = quizRepository.findById(id)
//                .orElseThrow(() -> new QuizNotFoundException("Quiz non trouvé avec l'ID : " + id));
//
//        UUID themeId = quiz.getTheme().getId();
//
//        // Vérification de l'unicité du titre dans le thème
//        if (quizCreateDTO.getName() != null && !quizCreateDTO.getName().isBlank()) {
//            boolean nameExists = quizRepository.existsByNameAndThemeId(quizCreateDTO.getName(), themeId);
//            if (!quiz.getName().equals(quizCreateDTO.getName()) && nameExists) {
//                throw new IllegalArgumentException("Un quiz avec le nom '" + quizCreateDTO.getName() + "' existe déjà dans ce thème.");
//            }
//            quiz.setName(quizCreateDTO.getName());
//        }
//
//        // Vérification de la position si elle est spécifiée
//        if (quizCreateDTO.getPosition() != null) {
//            int newPosition = quizCreateDTO.getPosition();
//
//            // Récupérer tous les quiz actifs du thème, triés par position
//            List<Quiz> activeQuizzes = quizRepository.findByThemeIdAndIsActive(themeId, true)
//                    .stream()
//                    .sorted(Comparator.comparingInt(Quiz::getPosition))
//                    .toList();
//
//            //debug
//            System.out.println("Active quizzes sorted by position: " + activeQuizzes);
//
//            // Vérifier l'unicité de la nouvelle position
//            boolean positionExists = activeQuizzes.stream()
//                    .anyMatch(q -> q.getPosition() == newPosition && !q.getId().equals(quiz.getId()));
//            if (positionExists) {
//                throw new IllegalArgumentException("L'ordre " + newPosition + " est déjà utilisé par un autre quiz actif.");
//            }
//
//            // Vérifier la cohérence des positions après mise à jour
//            List<Integer> positions = activeQuizzes.stream()
//                    .filter(q -> !q.getId().equals(quiz.getId())) // Exclure le quiz en cours de modification
//                    .map(Quiz::getPosition)
//                    .toList();
//            positions.add(newPosition); // Ajouter la nouvelle position proposée
//            positions.sort(Integer::compareTo);
//
//            for (int i = 0; i < positions.size(); i++) {
//                if (positions.get(i) != i + 1) {
//                    throw new IllegalArgumentException("Les positions des quiz actifs dans ce thème ne respectent pas une numérotation continue.");
//                }
//            }
//
//            // Attribuer la nouvelle position si toutes les vérifications passent
//            quiz.setPosition(newPosition);
//        }
//
//        // debug
//        // Avant d'enregistrer le quiz mis à jour
//        System.out.println("Saving quiz with updated position: " + quiz.getPosition());
//
//
//        // Sauvegarder les modifications
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        // debug
//        System.out.println("Saved quiz: " + updatedQuiz);
//
//        return modelMapper.map(updatedQuiz, QuizResponseDTO.class);
//    }

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
            // Si la position actuelle est en conflit ou n'existe pas, attribuer une nouvelle position
            if (quiz.getPosition() == null || quizRepository.findByThemeIdAndIsActive(themeId, true)
                    .stream()
                    .anyMatch(q -> q.getPosition().equals(quiz.getPosition()))) {
                quiz.setPosition(getMaxPosition(themeId) + 1);
            }
        } else {
            // Désactiver le quiz
            quiz.setPosition(null);
            quiz.setActive(false);

            // Sauvegarder le quiz avant de réorganiser les positions
            quizRepository.save(quiz);

            // Réorganiser les positions des quiz actifs
            reorganizePositions(themeId);
        }

        quiz.setActive(isActive);
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
}
