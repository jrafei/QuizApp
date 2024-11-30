package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.model.beans.Answer;
import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.dto.AnswerDTO;
import com.quizapp.quizApp.repository.AnswerRepository;
import com.quizapp.quizApp.service.interfac.AnswerService;
import com.quizapp.quizApp.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public AnswerServiceImpl(QuestionRepository questionRepository,
                             AnswerRepository answerRepository,
                             ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<AnswerDTO> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerDTO> getActiveAnswersByQuestion(UUID questionId) {
        return answerRepository.findAllByQuestionIdAndIsActiveTrueOrderByPosition(questionId).stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerDTO> getAllAnswersByQuestion(UUID questionId) {
        return answerRepository.findAllByQuestionIdOrderByPosition(questionId).stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDTO getAnswerById(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));
        return modelMapper.map(answer, AnswerDTO.class);
    }

    @Override
    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        // Vérifie si une réponse avec le même intitulé existe déjà pour la même question
        if (answerRepository.existsByLabelAndQuestionId(answerDTO.getLabel(), answerDTO.getQuestionId())) {
            throw new IllegalArgumentException("Une réponse avec le même intitulé existe déjà pour cette question.");
        }

        // Vérifie si la question associée existe
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable pour l'ID spécifié."));

        // Créer l'entité Answer
        Answer answer = new Answer();
        answer.setLabel(answerDTO.getLabel());
        answer.setQuestion(question);
        answer.setCorrect(answerDTO.getIsCorrect() != null ? answerDTO.getIsCorrect() : false);
        answer.setIsActive(false); // Désactivé par défaut
        answer.setPosition(null); // Position non définie par défaut

        // Sauvegarder l'entité
        Answer savedAnswer = answerRepository.save(answer);

        // Retourner le DTO
        return modelMapper.map(savedAnswer, AnswerDTO.class);
    }

    @Override
    public AnswerDTO updateAnswer(UUID id, AnswerDTO answerDTO) {
        System.out.println("Entree dans update answer");

        // Récupérer l'entité existante
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));

        System.out.println("Récupération existingAnswer ok");
        System.out.println("Existing Answer: " + existingAnswer);

        // Vérifiez que le DTO ne tente pas de modifier l'ID de la question associée
        if (answerDTO.getQuestionId() != null &&
                !answerDTO.getQuestionId().equals(existingAnswer.getQuestion().getId())) {
            throw new IllegalArgumentException("Modification de l'ID de la question associée non autorisée.");
        }

        System.out.println("Récupération associated question ok");
        System.out.println("Associated Question ID: " + (existingAnswer.getQuestion() != null ? existingAnswer.getQuestion().getId() : "null"));

        System.out.println("Checking existence for label: " + answerDTO.getLabel() +
                ", questionId: " + existingAnswer.getQuestion().getId() +
                ", excluding id: " + id);

        // Vérifie si une autre réponse avec le même intitulé existe
        if (answerDTO.getLabel() != null &&
                answerRepository.existsByLabelAndQuestionIdAndIdNot(answerDTO.getLabel(), existingAnswer.getQuestion().getId(), id)) {
            throw new IllegalArgumentException("Une réponse avec le même intitulé existe déjà pour cette question.");
        }

        // Vérifie s'il existe déjà une réponse correcte et active pour cette question
        boolean hasActiveCorrectAnswer = answerRepository.existsByQuestionIdAndCorrectTrueAndIsActiveTrue(existingAnswer.getQuestion().getId());
        if (answerDTO.getIsCorrect() != null && answerDTO.getIsActive() != null &&
                answerDTO.getIsCorrect() && answerDTO.getIsActive() && hasActiveCorrectAnswer) {
            throw new IllegalArgumentException("Une seule réponse correcte et active est autorisée par question.");
        }

        // Vérifier que la modification de la position est cohérente
        if (answerDTO.getPosition() != null) {
            validatePositionChange(existingAnswer.getQuestion().getId(), answerDTO.getPosition());
        }

        // Mappage des champs uniquement s'ils sont fournis
        if (answerDTO.getLabel() != null) {
            existingAnswer.setLabel(answerDTO.getLabel());
        }
        if (answerDTO.getIsCorrect() != null) { // Correction du champ
            existingAnswer.setCorrect(answerDTO.getIsCorrect());
        }
        if (answerDTO.getIsActive() != null) { // Correction du champ
            existingAnswer.setIsActive(answerDTO.getIsActive());
        }
        if (answerDTO.getPosition() != null) {
            existingAnswer.setPosition(answerDTO.getPosition());
        }

        System.out.println("Answer to save: " + existingAnswer);

        // Sauvegarder l'entité mise à jour
        Answer updatedAnswer = answerRepository.save(existingAnswer);

        System.out.println("Saved Answer: " + updatedAnswer);

        // Retourner le DTO mis à jour
        return modelMapper.map(updatedAnswer, AnswerDTO.class);
    }

    @Override
    public void setCorrectAnswer(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));

        // Vérifie s'il existe déjà une réponse correcte et active pour cette question
        boolean hasActiveCorrectAnswer = answerRepository.existsByQuestionIdAndCorrectTrueAndIsActiveTrue(answer.getQuestion().getId());
        if (hasActiveCorrectAnswer) {
            throw new IllegalArgumentException("Une seule réponse correcte et active est autorisée par question.");
        }

        answer.setCorrect(true);
        answer.setIsActive(true); // Rendre la réponse active en même temps
        answerRepository.save(answer);
    }


    @Override
    public void deleteAnswer(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));
        UUID questionId = answer.getQuestion().getId();
        answerRepository.deleteById(id);
        reorderAnswers(questionId); // Réorganiser après suppression
    }

    @Override
    public void activateAnswer(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));

        // Vérifie s'il existe déjà une réponse correcte et active pour cette question
        boolean hasActiveCorrectAnswer = answerRepository.existsByQuestionIdAndCorrectTrueAndIsActiveTrue(answer.getQuestion().getId());
        if (answer.getCorrect() && hasActiveCorrectAnswer) {
            throw new IllegalArgumentException("Une seule réponse correcte et active est autorisée par question.");
        }

        if (answer.getPosition() == null) {
            List<Answer> activeAnswers = answerRepository.findAllByQuestionIdAndIsActiveTrueOrderByPosition(
                    answer.getQuestion().getId());
            answer.setPosition(activeAnswers.size() + 1); // Ajoute à la fin
        }
        answer.setIsActive(true);
        answerRepository.save(answer);
    }

    @Override
    public void deactivateAnswer(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));
        answer.setIsActive(false);
        answer.setPosition(null);
        answerRepository.save(answer);
        reorderAnswers(answer.getQuestion().getId());
    }

    private void reorderAnswers(UUID questionId) {
        List<Answer> activeAnswers = answerRepository.findAllByQuestionIdAndIsActiveTrueOrderByPosition(questionId);
        for (int i = 0; i < activeAnswers.size(); i++) {
            activeAnswers.get(i).setPosition(i + 1); // Réordonne à partir de 1
        }
        answerRepository.saveAll(activeAnswers);
    }

    private void validatePositionChange(UUID questionId, int newPosition) {
        // Récupérer toutes les réponses actives pour cette question, triées par position
        List<Answer> activeAnswers = answerRepository.findAllByQuestionIdAndIsActiveTrueOrderByPosition(questionId);

        // Vérifie que la position demandée est comprise dans la plage valide
        if (newPosition < 1 || newPosition > activeAnswers.size()) {
            throw new IllegalArgumentException("La position demandée est incohérente avec l'ordre actuel des réponses actives.");
        }

        // Vérifie que la nouvelle position ne casse pas la continuité logique
        for (int i = 0; i < activeAnswers.size(); i++) {
            int expectedPosition = i + 1; // Les positions doivent être séquentielles
            if (activeAnswers.get(i).getPosition() != expectedPosition && expectedPosition != newPosition) {
                throw new IllegalArgumentException("La modification de la position casse l'ordre séquentiel des réponses actives.");
            }
        }
    }


}
