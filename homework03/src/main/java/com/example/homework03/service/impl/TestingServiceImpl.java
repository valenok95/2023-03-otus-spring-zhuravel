package com.example.homework03.service.impl;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.dao.QuestionDao;
import com.example.homework03.dao.UserDao;
import com.example.homework03.domain.Question;
import com.example.homework03.domain.User;
import com.example.homework03.service.IOService;
import com.example.homework03.service.TestingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class TestingServiceImpl implements TestingService {

    private final TestServiceProperties props;

    private int successCount;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final IOService ioService;
    private final MessageSource messageSource;

    @Autowired
    public TestingServiceImpl(TestServiceProperties props, QuestionDao questionDao,
                              UserDao userDao, IOService ioService) {
        this.props = props;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.ioService = ioService;
        var ms = new ResourceBundleMessageSource();
        ms.setDefaultLocale(props.getLocale());
        ms.setUseCodeAsDefaultMessage(true);
        ms.setBasename("i18n/appmessages");
        this.messageSource = ms;
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
        String firstName = ioService.readStringWithPrompt(getMessage("ask.firstname"));

        String lastName = ioService.readStringWithPrompt(getMessage("ask.lastname"));

        userDao.saveUser(new User(firstName, lastName));
    }

    private void greeting(List<Question> questionList) {
        ioService.printString(getMessage("welcome", userDao.getFullName()));
        ioService.printString(getMessage("question.asking.size", questionList.size()));
        ioService.printString(getMessage("question.asking.required", props.getNeedCountOfQuestionForSuccess()));
    }

    private void testingQuestions(List<Question> questionList) {
        questionList.forEach(this::testingQuestion);
    }

    private void testingQuestion(Question question) {
        String questionTitle = getMessage(question.getTitleParam());

        ioService.printString(getMessage("question.asking.title",
                String.valueOf(question.getId()), questionTitle));

        ioService.printString(getMessage(question.getTextParam(), question.getTask()));

        ioService.printString(getMessage("question.asking.answer.options"));


        question.getAnswersValue().forEach(el -> ioService.printString("\t" + el));

        String answer =
                ioService.readStringWithPrompt(getMessage("question.asking.answer.enter"));

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
        ioService.printString(getMessage("result.named", userDao.getFullName()));
        ioService.printString(getMessage("result.count", successCount, questionList.size()));
        ioService.printString(getMessage("result.min", props.getNeedCountOfQuestionForSuccess()));
        ioService.printString(successCount < props.getNeedCountOfQuestionForSuccess() ?
                getMessage("result.failed") :
                getMessage("result.success"));
    }

    public String getMessage(String code, Object... args) { // Метод для получения сообщения по коду
        return this.messageSource.getMessage(code, args, props.getLocale());
    }


}
