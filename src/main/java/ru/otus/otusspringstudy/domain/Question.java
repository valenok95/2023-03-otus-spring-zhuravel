package ru.otus.otusspringstudy.domain;

import java.util.List;
import lombok.Data;

@Data
public class Question {
    private String text;
    
    private List<String> ans;
    
    public boolean hasAns(){
        return !ans.isEmpty();
    }
}
