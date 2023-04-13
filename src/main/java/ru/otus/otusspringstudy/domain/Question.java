package ru.otus.otusspringstudy.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Question {
    private String text;

    private List<String> ans = new ArrayList<>();
    
    private int indexOfRightAnswer;

    public boolean hasAns() {
        return !ans.isEmpty();
    }

    public String getAnswerVariantsString() {
        return String.join(" | ", ans);
    }
}
