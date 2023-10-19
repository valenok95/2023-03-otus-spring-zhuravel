package com.example.homework03.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Question {
    private final Long id;

    private final String titleParam;

    private final String textParam;
    private final String task;

    private final List<Answer> answers;

    public List<String> getAnswersValue() {
        return this.answers.stream().map(Answer::getValue).collect(Collectors.toList());
    }

    public Answer getCorrectAnswer() {
        return this.answers.stream().filter(Answer::isCorrect).findFirst().orElseThrow();
    }

    public boolean checkOption(String option) {
        return getCorrectAnswer().getValue().equalsIgnoreCase(option);
    }
}
