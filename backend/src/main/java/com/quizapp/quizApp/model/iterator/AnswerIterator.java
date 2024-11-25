package com.quizapp.quizApp.model.iterator;

import com.quizapp.quizApp.model.beans.Answer;

import java.util.List;

public class AnswerIterator implements Iterator {
    private List<Answer> answers;
    private int index;

    public AnswerIterator(List<Answer> answers){
        this.answers = answers;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < answers.size();
    }

    @Override
    public Object next() {
        if (hasNext()){
            return answers.get(index++);
        }
        return null;
    }

}
