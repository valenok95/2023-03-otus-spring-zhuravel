package ru.otus.otusspringstudy.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.otusspringstudy.dao.QuestionDao;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;

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
