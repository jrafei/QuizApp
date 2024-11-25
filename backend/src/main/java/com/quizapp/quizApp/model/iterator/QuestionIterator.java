package com.quizapp.quizApp.model.iterator;

import com.quizapp.quizApp.model.beans.Question;
import java.util.List;

public class QuestionIterator implements Iterator{
    private List<Question> questions;
    private int index;

    public QuestionIterator(List<Question> questions){
        this.questions = questions;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < questions.size();
    }

    @Override
    public Object next() {
        if (hasNext()){
            return questions.get(index++);
        }
        return null;
    }
}
