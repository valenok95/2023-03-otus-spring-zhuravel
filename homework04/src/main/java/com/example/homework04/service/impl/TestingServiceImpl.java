package com.example.homework04.service.impl;

import com.example.homework04.configuration.TestServiceProperties;
import com.example.homework04.dao.QuestionDao;
import com.example.homework04.dao.UserDao;
import com.example.homework04.domain.Question;
import com.example.homework04.domain.User;
import com.example.homework04.service.IOService;
import com.example.homework04.service.TestingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestingServiceImpl implements TestingService {

    private final TestServiceProperties props;
    private int successCount;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final IOService ioService;
    private final LocalizeServiceImpl localizeService;

    @Autowired
    public TestingServiceImpl(TestServiceProperties props, QuestionDao questionDao,
                              UserDao userDao, IOService ioService, LocalizeServiceImpl localizeService) {
        this.props = props;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.ioService = ioService;
        this.localizeService = localizeService;
    }

    @Override
    public void testing() {
        List<Question> questionList = questionDao.getAll();
        successCount = 0;
        askFullName();
        greeting(questionList);
        testingQuestions(questionList);
        printResult(questionList);
    }

    private void askFullName() {
        String firstName = ioService.readStringWithPrompt(localizeService.getMessage("ask.firstname"));

        String lastName = ioService.readStringWithPrompt(localizeService.getMessage("ask.lastname"));

        userDao.saveUser(new User(firstName, lastName));
    }

    private void greeting(List<Question> questionList) {
        ioService.printString(localizeService.getMessage("welcome", userDao.getUser().getFullName()));
        ioService.printString(localizeService.getMessage("question.asking.size", questionList.size()));
        ioService.printString(localizeService.getMessage("question.asking.required", props.getNeedCountOfQuestionForSuccess()));
    }

    private void testingQuestions(List<Question> questionList) {
        questionList.forEach(this::testingQuestion);
    }

    private void testingQuestion(Question question) {
        String questionTitle = localizeService.getMessage(question.getTitleParam());

        ioService.printString(localizeService.getMessage("question.asking.title",
                String.valueOf(question.getId()), questionTitle));

        ioService.printString(localizeService.getMessage(question.getTextParam(), question.getTask()));

        ioService.printString(localizeService.getMessage("question.asking.answer.options"));


        question.getAnswersValue().forEach(el -> ioService.printString("\t" + el));

        String answer =
                ioService.readStringWithPrompt(localizeService.getMessage("question.asking.answer.enter"));

        ioService.printString(String.valueOf(check(answer, question)));
    }

    private boolean check(String answer, Question question) {
        boolean verdict = question.checkOption(answer);
        if (verdict) {
            successCount++;
        }
        return verdict;
    }

    private void printResult(List<Question> questionList) {
        ioService.printString(localizeService.getMessage("result.named", userDao.getUser().getFullName()));
        ioService.printString(localizeService.getMessage("result.count", successCount, questionList.size()));
        ioService.printString(localizeService.getMessage("result.min", props.getNeedCountOfQuestionForSuccess()));
        ioService.printString(successCount < props.getNeedCountOfQuestionForSuccess() ?
                localizeService.getMessage("result.failed") :
                localizeService.getMessage("result.success"));
    }

 


}
