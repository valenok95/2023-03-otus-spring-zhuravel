package com.example.homework03.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Message {
    private final String askFirstNameParam;
    private final String askLastNameParam;
    private final String welcomeParam;
    private final String questionSizeParam;
    private final String requiredCountParam;
    private final String questionTitleParam;
    private final String answerOptionsParam;
    private final String answerEnterParam;
    private final String resultNamedParam;
    private final String resultCountParam;
    private final String resultMinParam;
    private final String resultSuccessParam;
    private final String resultFailedParam;
}
