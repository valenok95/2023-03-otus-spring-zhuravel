package com.example.homework03.service.impl;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.dao.MessageDao;
import com.example.homework03.dao.QuestionDao;
import com.example.homework03.dao.UserDao;
import com.example.homework03.domain.Question;
import com.example.homework03.domain.User;
import com.example.homework03.service.IOService;
import com.example.homework03.service.TestingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TestingServiceImpl implements TestingService {

    private final TestServiceProperties props;

    private int successCount;
    private final QuestionDao questionDao;
    private final MessageDao messageDao;
    private final UserDao userDao;
    private final IOService ioService;
    private final MessageSource messageSource;

    @Autowired
    public TestingServiceImpl(TestServiceProperties props, QuestionDao questionDao,
                              MessageDao messageDao, UserDao userDao, IOService ioService, MessageSource messageSource) {
        this.props = props;
        this.questionDao = questionDao;
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.ioService = ioService;
        this.messageSource = messageSource;
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
        String firstName =
                ioService.readStringWithPrompt(messageSource.getMessage(messageDao.getMessage().getAskFirstNameParam(), new String[]{},
                        props.getLocale()));

        String lastName =
                ioService.readStringWithPrompt(messageSource.getMessage(messageDao.getMessage().getAskLastNameParam(), new String[]{},
                        props.getLocale()));

        userDao.saveUser(new User(firstName, lastName));
    }

    private void greeting(List<Question> questionList) {
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getWelcomeParam(),
                new String[]{userDao.getFullName()},
                props.getLocale()));
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getQuestionSizeParam(),
                new Integer[]{questionList.size()},
                props.getLocale()));
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getRequiredCountParam(),
                new Integer[]{props.getNeedCountOfQuestionForSuccess()},
                props.getLocale()));
    }

    private void testingQuestions(List<Question> questionList) {
        questionList.forEach(this::testingQuestion);
    }

    private void testingQuestion(Question question) {
        String questionTitle =
                messageSource.getMessage(question.getTitleParam(),
                        new String[]{},
                        props.getLocale());

        ioService.printString(messageSource.getMessage(messageDao.getMessage().getQuestionTitleParam(),
                new String[]{String.valueOf(question.getId()), questionTitle},
                props.getLocale()));
        //  ioService.printString("\nQuestion №" + question.getId() + " - " + question.getTitle());


        ioService.printString(messageSource.getMessage(question.getTextParam(),
                new String[]{question.getTask()},
                props.getLocale()));

        //   ioService.printString(question.getTextParam());

        ioService.printString(messageSource.getMessage(messageDao.getMessage().getAnswerOptionsParam(),
                new String[]{},
                props.getLocale()));

        question.getAnswersValue().forEach(el -> ioService.printString("\t" + el));

        String answer =
                ioService.readStringWithPrompt(messageSource.getMessage(messageDao.getMessage().getAnswerEnterParam(),
                        new String[]{},
                        props.getLocale()));

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
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getResultNamedParam(),
                new String[]{userDao.getFullName()},
                props.getLocale()));
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getResultCountParam(),
                new Integer[]{successCount, questionList.size()},
                props.getLocale()));
        ioService.printString(messageSource.getMessage(messageDao.getMessage().getResultMinParam(),
                new Integer[]{props.getNeedCountOfQuestionForSuccess()},
                props.getLocale()));
        ioService.printString(successCount < props.getNeedCountOfQuestionForSuccess() ?
                messageSource.getMessage(messageDao.getMessage().getResultFailedParam(),
                        new String[]{},
                        props.getLocale()) : messageSource.getMessage(messageDao.getMessage().getResultSuccessParam(),
                new String[]{},
                props.getLocale()));
    }
}
