package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.model.beans.Answer;
import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.QuestionDTO;
import com.quizapp.quizApp.repository.QuestionRepository;
import com.quizapp.quizApp.repository.QuizRepository;
import com.quizapp.quizApp.service.interfac.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository; // Ajout de QuizRepository
    private final ModelMapper modelMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               QuizRepository quizRepository, // Injecter QuizRepository ici
                               ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository; // Initialiser QuizRepository
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getActiveQuestionsByQuiz(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(quizId);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getAllQuestionsByQuiz(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuizIdOrderByPosition(quizId);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionById(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));
        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        System.out.println("Received QuestionDTO: {}" + questionDTO);

        // Vérifie si l'ID du quiz est valide
        if (!quizRepository.existsById(questionDTO.getQuizId())) {
            throw new IllegalArgumentException("L'ID du quiz spécifié est invalide ou introuvable.");
        }
        System.out.println("Quiz ID is valid: {}" + questionDTO.getQuizId());

        // Vérifie si une question avec le même libellé existe déjà dans le quiz
        if (questionRepository.existsByLabelAndQuizId(questionDTO.getLabel(), questionDTO.getQuizId())) {
            throw new IllegalArgumentException("Une question avec le même libellé existe déjà dans ce quiz.");
        }
        System.out.println("Question label is unique for the quiz.");

        Question question = modelMapper.map(questionDTO, Question.class);
        System.out.println("Mapped Question entity: {}" +  question);

        question.setIsActive(false); // Par défaut
        question.setPosition(null); // Par défaut

        // Ajouter les réponses si présentes
        if (questionDTO.getAnswers() != null && !questionDTO.getAnswers().isEmpty()) {
            List<Answer> answers = questionDTO.getAnswers().stream()
                    .map(answerDTO -> {
                        Answer answer = modelMapper.map(answerDTO, Answer.class);
                        answer.setQuestion(question); // Associer la réponse à la question

                        // Définir une valeur par défaut pour isActive
                        if (answer.getIsActive() == null) {
                            answer.setIsActive(false);
                        }
                        // Définir une valeur par défaut pour isCorrect
                        if (answer.getCorrect() == null) {
                            answer.setCorrect(false);
                        }

                        return answer;
                    })
                    .collect(Collectors.toList());
            question.setAnswers(answers);
        }
        System.out.println("Final Question entity with answers: {}" + question);

        Question savedQuestion = questionRepository.save(question);
        System.out.println("Saved Question entity: {}" + savedQuestion);

        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }

    @Override
    public QuestionDTO updateQuestion(UUID id, QuestionDTO questionDTO) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));

        // Vérifie si une autre question avec le même libellé existe déjà dans le même quiz
        if (questionDTO.getLabel() != null &&
                questionRepository.existsByLabelAndQuizIdAndIdNot(
                        questionDTO.getLabel(),
                        existingQuestion.getQuiz().getId(),
                        id)) {
            throw new IllegalArgumentException("Une question avec le même libellé existe déjà dans ce quiz.");
        }

        // Vérifie si la position demandée est cohérente
        if (questionDTO.getPosition() != null) {
            validatePositionChange(existingQuestion.getQuiz().getId(), questionDTO.getPosition());
            reorderQuestionsAfterPositionChange(existingQuestion, questionDTO.getPosition());
        }

        // Mappage des champs uniquement s'ils sont fournis
        if (questionDTO.getLabel() != null) {
            existingQuestion.setLabel(questionDTO.getLabel());
        }
        if (questionDTO.getIsActive() != null) {
            existingQuestion.setIsActive(questionDTO.getIsActive());
        }

        // Sauvegarder la question mise à jour
        Question updatedQuestion = questionRepository.save(existingQuestion);

        // Retourner le DTO mis à jour
        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }


    @Override
    public void deleteQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));
        UUID quizId = question.getQuiz().getId();
        questionRepository.deleteById(id);
        reorderQuestions(quizId); // Réorganiser après suppression
    }

    @Override
    public void activateQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));

        // Validation avant activation
        validateQuestionActivation(question);

        if (question.getPosition() == null) {
            List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(
                    question.getQuiz().getId());
            question.setPosition(activeQuestions.size() + 1); // Ajoute à la fin
        }
        question.setIsActive(true);
        questionRepository.save(question);
    }

    @Override
    public void deactivateQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));
        question.setIsActive(false);
        question.setPosition(null); // Annule la position
        questionRepository.save(question);
        reorderQuestions(question.getQuiz().getId()); // Réorganiser après désactivation
    }


    private void reorderQuestions(UUID quizId) {
        List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(quizId);
        for (int i = 0; i < activeQuestions.size(); i++) {
            activeQuestions.get(i).setPosition(i + 1); // Réordonne à partir de 1
        }
        questionRepository.saveAll(activeQuestions);
    }

    private void validatePositionChange(UUID quizId, Integer newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }

        // Récupérer toutes les questions actives triées par position
        List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(quizId);

        // Vérifie que la position demandée est dans la plage valide
        if (newPosition < 1 || newPosition > activeQuestions.size()) {
            throw new IllegalArgumentException("La position demandée est incohérente avec l'ordre actuel des questions actives.");
        }

        // Vérifie que la nouvelle position ne casse pas la continuité logique
        for (int i = 0; i < activeQuestions.size(); i++) {
            int expectedPosition = i + 1; // Les positions doivent être séquentielles
            if (activeQuestions.get(i).getPosition() != expectedPosition && expectedPosition != newPosition) {
                throw new IllegalArgumentException("La modification de la position casse l'ordre séquentiel des questions actives.");
            }
        }
    }

    private void reorderQuestionsAfterPositionChange(Question question, Integer newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }

        // Récupérer toutes les questions actives triées par position
        List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(question.getQuiz().getId());

        // Retirer temporairement la question modifiée de la liste
        activeQuestions.removeIf(q -> q.getId().equals(question.getId()));

        // Insérer la question modifiée à la nouvelle position
        activeQuestions.add(newPosition - 1, question);

        // Réattribuer les positions séquentielles
        for (int i = 0; i < activeQuestions.size(); i++) {
            activeQuestions.get(i).setPosition(i + 1);
        }

        // Sauvegarder les questions réorganisées
        questionRepository.saveAll(activeQuestions);
    }

    private void validateQuestionActivation(Question question) {
        List<Answer> activeAnswers = question.getAnswers().stream()
                .filter(Answer::getIsActive) // Filtrer uniquement les réponses actives
                .collect(Collectors.toList());

        if (activeAnswers.size() < 2) {
            throw new IllegalArgumentException("Une question doit avoir au moins deux réponses actives pour être activée.");
        }

        long correctAnswersCount = activeAnswers.stream()
                .filter(Answer::getCorrect) // Compter uniquement les réponses correctes actives
                .count();

        if (correctAnswersCount != 1) {
            throw new IllegalArgumentException("Une question doit avoir exactement une réponse correcte active pour être activée.");
        }
    }

}
