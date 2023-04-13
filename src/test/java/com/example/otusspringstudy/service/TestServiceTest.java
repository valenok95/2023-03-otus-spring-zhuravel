package com.example.otusspringstudy.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.otusspringstudy.dao.QuestionDao;
import ru.otus.otusspringstudy.domain.Question;
import ru.otus.otusspringstudy.service.TestService;
import ru.otus.otusspringstudy.service.TestServiceImpl;

public class TestServiceTest {
    @Test
    void tests() throws IOException {
        // preparing expected test data
        List<String> variants1 = List.of("1.beef", "2.watermelon");
        List<Question> preparedQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setText("What is sweet?");
        question1.setAns(variants1);
        question1.setIndexOfRightAnswer(2);
        Question question2 = new Question();
        question2.setText("Who are you?");
        question2.setIndexOfRightAnswer(0);
        preparedQuestions.add(question1);
        preparedQuestions.add(question2);
        QuestionDao questionDao = Mockito.mock(QuestionDao.class);
        Mockito.when(questionDao.readQuestions()).thenReturn(preparedQuestions);
        //Ожидаемый вывод 
        List<String> expectedOutputList = new ArrayList<>();
        expectedOutputList.add("Enter your full name: ");
        expectedOutputList.add("The next question is - What is sweet?");
        expectedOutputList.add("1.beef | 2.watermelon");
        expectedOutputList.add("The next question is - Who are you?");
        expectedOutputList.add("Robot Machine your result is: 2/2");
// имитируем ввод пользователя
        InputStream inputStream = new ByteArrayInputStream("Robot Machine\n2\n1000".getBytes());
        // перехватываем поток вывода в консоль
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        //запускается сам тест
        TestService testService = new TestServiceImpl(questionDao, inputStream);
        testService.performTest();
        //
        String resultOutput = baos.toString();
        List<String> resultOutputList = resultOutput.lines().collect(Collectors.toList());
        // проверяем соответствие вывода ожидаемому
        Assertions.assertIterableEquals(resultOutputList, expectedOutputList);
    }
}
