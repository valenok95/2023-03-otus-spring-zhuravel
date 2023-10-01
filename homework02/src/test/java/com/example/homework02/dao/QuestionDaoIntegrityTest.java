package com.example.homework02.dao;

import com.example.homework02.config.ApplicationTestConfig;
import com.example.homework02.domain.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationTestConfig.class, QuestionDaoImpl.class})
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
        var title = question.getTitle();
        Assertions.assertEquals("HEAD", title);
        var text = question.getText();
        Assertions.assertEquals("BODY", text);
        var correctAnswer = question.getCorrectAnswer();
        Assertions.assertEquals(new Answer("ANSWER", true), correctAnswer);
        var answers = question.getAnswers();
        var expectedAnswers = List.of(
                new Answer("WRONG_ANSWER", false),
                new Answer("ANSWER", true),
                new Answer("NOT_ANSWER", false)
        );
        Assertions.assertEquals(expectedAnswers, answers);
        var options = question.getAnswersValue();
        Assertions.assertEquals(List.of("WRONG_ANSWER", "ANSWER", "NOT_ANSWER"), options);
    }
}
