package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.model.beans.Answer;
import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.dto.AnswerDTO;
import com.quizapp.quizApp.model.dto.response.AnswerResponseDTO;
import com.quizapp.quizApp.repository.AnswerRepository;
import com.quizapp.quizApp.service.interfac.AnswerService;
import com.quizapp.quizApp.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

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
    @Transactional
    public AnswerDTO createAnswer(AnswerDTO answerDTO) { //OK

        // verifie l'existence de question
        if (!questionRepository.existsById(answerDTO.getQuestionId())) {
            throw new EntityNotFoundException("Invalid questionId: " + answerDTO.getQuestionId());
        }

        // Vérifie si une réponse correcte existe déjà pour la question
        if (answerDTO.getIsCorrect() != null && answerDTO.getIsCorrect()) {
            boolean hasActiveCorrectAnswer = answerRepository.existsByQuestionIdAndCorrectTrueAndIsActiveTrue(answerDTO.getQuestionId());
            if (hasActiveCorrectAnswer) {
                throw new IllegalArgumentException("Une seule réponse correcte et active est autorisée par question.");
            }
        }

        // Vérifie si une réponse avec le même intitulé existe déjà pour la même question
        if (answerRepository.existsByLabelAndQuestionId(answerDTO.getLabel(), answerDTO.getQuestionId())) {
            throw new IllegalArgumentException("Une réponse avec le même intitulé existe déjà pour cette question.");
        }

        // Vérifie si la question associée existe
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable pour l'ID spécifié."));

        Answer answer = modelMapper.map(answerDTO,Answer.class);
        answer.setCorrect(answerDTO.getIsCorrect() != null ? answerDTO.getIsCorrect() : false);

        answer.setIsActive(false); // Désactivé par défaut

        // Determine the next position for the new question
        int nextPosition = question.getAnswers().size()+1; // A VERIFIER
        answer.setPosition(nextPosition);

        // Sauvegarder l'entité
        Answer savedAnswer = answerRepository.save(answer);

        //question.addAnswer(answer); // pas nécessaire , ca se fait automatiquement à cause de cascade

        // Retourner le DTO
        return modelMapper.map(savedAnswer, AnswerDTO.class);
    }


    @Override
    public AnswerDTO updateAnswer(UUID id, AnswerDTO answerDTO) { //OK
        System.out.println("Entree dans update answer");

        // Récupérer l'entité existante
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));

        System.out.println("Récupération existingAnswer ok");


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
            validatePositionChange(existingAnswer,existingAnswer.getQuestion().getId(), answerDTO.getPosition());
            permutePosition(existingAnswer, answerDTO.getPosition());
            System.out.println("Permutation des position done");
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

        // Sauvegarder l'entité mise à jour
        Answer updatedAnswer = answerRepository.save(existingAnswer);


        // Retourner le DTO mis à jour
        return modelMapper.map(updatedAnswer, AnswerDTO.class);
    }

    @Override
    public void setCorrectAnswer(UUID id) { // OK
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
    public void deleteAnswer(UUID id) { //OK
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));
        UUID questionId = answer.getQuestion().getId();
        answerRepository.deleteById(id);
        reorderAnswers(questionId); // Réorganiser après suppression
    }

    @Override
    public void activateAnswer(UUID id) { //OK
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
    public void deactivateAnswer(UUID id) { //ok
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réponse introuvable"));
        answer.setIsActive(false);
        answer.setPosition(null);
        answerRepository.save(answer);
        reorderAnswers(answer.getQuestion().getId());
    }

    private void reorderAnswers(UUID questionId) {
        List<Answer> activeAnswers = answerRepository.findAllByQuestionId(questionId);
        for (int i = 0; i < activeAnswers.size(); i++) {
            activeAnswers.get(i).setPosition(i + 1); // Réordonne à partir de 1
        }
        answerRepository.saveAll(activeAnswers);
    }

    private void validatePositionChange(Answer existingAnswer,UUID questionId, Integer newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }

        // Récupérer toutes les réponses pour cette question
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        // Vérifie que la position demandée est comprise dans la plage valide
        if (newPosition < 1 || newPosition > answers.size()) {
            throw new IllegalArgumentException("La position demandée est incohérente avec l'ordre actuel des réponses actives.");
        }

        if (existingAnswer.getPosition().equals(newPosition)) {
            throw new IllegalArgumentException("La position demandée est déjà affecté au question.");
        }

    }


    private void permutePosition(Answer answer, Integer newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("La position ne peut pas être nulle.");
        }

        Answer answAPermuter = answerRepository.findAnswerByQuestionIdAndPosition(answer.getQuestion().getId(), newPosition);

        answAPermuter.setPosition(answer.getPosition());
        answer.setPosition(newPosition);

        // Sauvegarder les questions réorganisées
        answerRepository.save(answAPermuter);
        answerRepository.save(answer);
    }

}
