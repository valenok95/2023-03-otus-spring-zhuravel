package com.example.homework04.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuestionTest {

    private static final List<String> testAnswers = List.of("1", "2", "3", "4");
    private static final String wrightAnswer = "4";

    private Question question;

    @BeforeEach
    void beforeEach() {
        List<Answer> answers = testAnswers.stream().map(el -> new Answer(el, el.equals(wrightAnswer))).toList();
        question = new Question(1L, "question.title.addition", "question.text", "2+2?", answers);
    }

    @Test
    void getAnswersValuesTest() {
        var answersValues = question.getAnswersValue();
        Assertions.assertEquals(answersValues, testAnswers);
    }

    @Test
    void getCorrectAnswerTest() {
        var answerValue = question.getCorrectAnswer();
        Assertions.assertEquals(answerValue.getValue(), wrightAnswer);
        Assertions.assertTrue(answerValue.isCorrect());
    }

    @Test
    void checkOptionTest() {
        var result1 = question.checkOption("4");
        Assertions.assertTrue(result1);
        var result2 = question.checkOption("5");
        Assertions.assertFalse(result2);
    }

}
