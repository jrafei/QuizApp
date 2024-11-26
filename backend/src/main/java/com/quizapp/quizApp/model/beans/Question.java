package com.quizapp.quizApp.model.beans;



import com.quizapp.quizApp.model.iterator.AnswerIterator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Iterator;
import java.util.List;
import com.quizapp.quizApp.model.iterator.Container;

@Entity
@Table(name = "QUESTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Container{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label", length = 60, nullable = false)
    private String label;

    @Column(name= "isActive", nullable = false)
    private boolean isActive;

    @Column(name= "position", nullable = false)
    private int position;

    @OneToOne
    @JoinColumn(name = "correct_answer_id", nullable = false)
    private Answer correctAnswer;

    @OneToMany(mappedBy="question",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> possibleAnswers;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)// Clé étrangère vers Quiz
    private Quiz quiz;


    @Override
    public AnswerIterator getIterator(){
        return new AnswerIterator(possibleAnswers);
    }

}
