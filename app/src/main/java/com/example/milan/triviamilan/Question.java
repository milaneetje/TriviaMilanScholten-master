package com.example.milan.triviamilan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question  implements Serializable {

    String questionString;
    String corAnswerString;
    String[] answers;
    String typeString;

    public Question(String questionString, String corAnswerString, String[] answers) {
        this.questionString = questionString;
        this.corAnswerString = corAnswerString;
        this.answers = answers;
    }

    public String getQuestionString() {
        return questionString;
    }

    public String getCorAnswerString() {
        return corAnswerString;
    }

    public String[] getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return ("Question: "+this.getQuestionString()+
                " Correct answer : "+ this.getCorAnswerString() +
                " All Answers: "+ this.getAnswers());
    }
}
