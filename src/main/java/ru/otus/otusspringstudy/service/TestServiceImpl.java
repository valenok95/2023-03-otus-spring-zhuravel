package ru.otus.otusspringstudy.service;

import java.io.IOException;
import ru.otus.otusspringstudy.dao.QuestionDao;

public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;

    public TestServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void listQuestions() throws IOException {
        questionDao.readQuestions().forEach(question -> {
            System.out.println(question.getText());
            if (question.hasAns()) {
                System.out.println(question.getAns());
            } else {
                System.out.println();
            }
        });
    }
}
