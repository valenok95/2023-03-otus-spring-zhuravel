package ru.otus.otusspringstudy.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.otusspringstudy.dao.QuestionDao;
import ru.otus.otusspringstudy.domain.Question;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;
    
    private final Scanner scanner;

    @Autowired
    public TestServiceImpl(QuestionDao questionDao,
                           @Value("#{ T(java.lang.System).in}") InputStream inputStream) {
        this.questionDao = questionDao;
        this.scanner = new Scanner(inputStream);
    }

    public void performTest() throws IOException {
        List<Question> questions = questionDao.readQuestions();
        System.out.println("Enter your full name: ");
        String username = scanner.nextLine();
        AtomicInteger scores = new AtomicInteger();
        for (Question question : questions) {
            log.info("The next question is - " + question.getText());
            System.out.println("The next question is - " + question.getText());
            boolean hasAnswers = question.hasAns();
            if (hasAnswers) {
                System.out.println(question.getAnswerVariantsString());
            }
            String response = scanner.nextLine();
            if (!hasAnswers || isGoodResponse(question, response)) {
                scores.getAndIncrement();
            }
        }
        System.out.println(String.format("%s your result is: %d/%d", username, scores.intValue(),
                questions.size()));
    }

    public boolean isGoodResponse(Question question, String result) {
        if (!question.hasAns()) {
            return true;
        }
        int resultNumber = Integer.parseInt(result);
        return resultNumber == question.getIndexOfRightAnswer();
    }
}
