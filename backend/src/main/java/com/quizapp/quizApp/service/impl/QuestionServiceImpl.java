package com.quizapp.quizApp.service.impl;

import com.quizapp.quizApp.model.beans.Question;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.QuestionDTO;
import com.quizapp.quizApp.repository.QuestionRepository;
import com.quizapp.quizApp.service.interfac.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
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
        Question question = modelMapper.map(questionDTO, Question.class);
        question.setActive(false); // Par défaut
        question.setPosition(null); // Par défaut
        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }

    @Override
    public QuestionDTO updateQuestion(UUID id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));
        modelMapper.map(questionDTO, question); // Mise à jour des champs
        Question updatedQuestion = questionRepository.save(question);
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
        if (question.getPosition() == null) {
            List<Question> activeQuestions = questionRepository.findAllByQuizIdAndIsActiveTrueOrderByPosition(
                    question.getQuiz().getId());
            question.setPosition(activeQuestions.size() + 1); // Ajoute à la fin
        }
        question.setActive(true);
        questionRepository.save(question);
    }

    @Override
    public void deactivateQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question introuvable"));
        question.setActive(false);
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

}
