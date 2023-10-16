package com.example.homework04.dao;

import com.example.homework04.domain.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class QuestionDaoIntegrityTest {
    @Autowired
    private QuestionDao questionDao;

    @Test
    public void integrityDaoTest() {
        // получаем список вопросов, вызывая тестируемый метод
        var result = questionDao.getAll();

        // проверяем, что получили ровно 1 вопрос из тестового csv в тестовых ресурсах
        Assertions.assertEquals(1, result.size());
        var question = result.get(0);

        // проверяем, что поля смапились правильно
        var id = question.getId();
        Assertions.assertEquals(1, id);
        var title = question.getTitleParam();
        Assertions.assertEquals("question.title.addition", title);
        var text = question.getTextParam();
        Assertions.assertEquals("question.text", text);
        var task = question.getTask();
        Assertions.assertEquals("2+2?", task);
        var correctAnswer = question.getCorrectAnswer();
        Assertions.assertEquals(new Answer("4", true), correctAnswer);
        var answers = question.getAnswers();
        var expectedAnswers = List.of(
                new Answer("3", false),
                new Answer("4", true),
                new Answer("6", false),
                new Answer("7", false)
        );
        Assertions.assertEquals(expectedAnswers, answers);
        var options = question.getAnswersValue();
        Assertions.assertEquals(List.of("3", "4", "6", "7"), options);
    }
}
