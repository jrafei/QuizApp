package com.quizapp.quizApp.config;

import com.quizapp.quizApp.model.beans.Answer;
import com.quizapp.quizApp.model.beans.Quiz;
import com.quizapp.quizApp.model.dto.AnswerDTO;
import com.quizapp.quizApp.model.dto.creation.QuizCreateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapping explicite entre QuizCreateDTO et Quiz
        modelMapper.addMappings(new PropertyMap<QuizCreateDTO, Quiz>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setPosition(source.getPosition());
                // Ignorer `creatorId` et `themeId`
                skip(destination.getCreator());
                skip(destination.getTheme());
                skip(destination.getId());
            }
        });

        // Mapping unique pour Answer -> AnswerDTO
        modelMapper.typeMap(Answer.class, AnswerDTO.class).addMappings(mapper -> {
            mapper.map(Answer::getCorrect, AnswerDTO::setIsCorrect);
            mapper.map(src -> src.getQuestion().getId(), AnswerDTO::setQuestionId); // Mappe uniquement l'ID
        });

        // Mapping unique pour AnswerDTO -> Answer
        modelMapper.typeMap(AnswerDTO.class, Answer.class).addMappings(mapper -> {
            mapper.map(AnswerDTO::getIsCorrect, Answer::setCorrect);
            mapper.skip(AnswerDTO::getQuestionId, Answer::setQuestion); // Ignore la relation complète
        });

        return modelMapper;
    }
}
