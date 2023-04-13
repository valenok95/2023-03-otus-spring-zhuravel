package com.example.otusspringstudy.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.otusspringstudy.dao.QuestionDao;
import ru.otus.otusspringstudy.dao.QuestionDaoSimple;
import ru.otus.otusspringstudy.domain.Question;

public class QuestionDaoTest {
    @Test
    void readQuestionsTest() throws IOException {
        // preparing expected test data
        List<String> variants1 = List.of("1.beef", "2.watermelon");
        List<Question> expectedQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setText("What is sweet?");
        question1.setAns(variants1);
        question1.setIndexOfRightAnswer(2);
        Question question2 = new Question();
        question2.setText("Who are you?");
        question2.setIndexOfRightAnswer(0);
        expectedQuestions.add(question1);
        expectedQuestions.add(question2);
        QuestionDao questionDao = new QuestionDaoSimple("questions.csv");
        List<Question> actualQuestions = questionDao.readQuestions();
        Assertions.assertIterableEquals(expectedQuestions, actualQuestions);
    }

}
