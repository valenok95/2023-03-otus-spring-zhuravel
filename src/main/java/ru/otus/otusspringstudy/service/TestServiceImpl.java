package ru.otus.otusspringstudy.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import ru.otus.otusspringstudy.dao.QuestionDao;

@Slf4j
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;

    public TestServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void listQuestions() throws IOException {
        questionDao.readQuestions().forEach(question -> {
            log.info("question text is " + question.getText());
            if (question.hasAns()) {
                log.info("question ans is " + question.getAns());
            }
        });
    }
}
