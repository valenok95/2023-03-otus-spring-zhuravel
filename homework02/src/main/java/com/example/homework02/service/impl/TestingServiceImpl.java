package com.example.homework02.service.impl;

import com.example.homework02.dao.QuestionDao;
import com.example.homework02.domain.Question;
import com.example.homework02.service.IOService;
import com.example.homework02.service.TestingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestingServiceImpl implements TestingService {

    private int successCount;
    private final int needSuccessCount;
    private final QuestionDao questionDao;
    private final IOService ioService;

    @Autowired
    public TestingServiceImpl(@Value("${questions.needCountOfQuestionForSuccess}") int needSuccessCount, QuestionDao questionDao,
                              IOService ioService) {
        this.needSuccessCount = needSuccessCount;
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public void testing() {
        List<Question> questionList = questionDao.getAll();
        successCount = 0;
        String fullName = askAndGetFullName();
        greeting(questionList, fullName);
        testingQuestions(questionList);
        printResult(questionList, fullName);
    }

    private String askAndGetFullName() {
        return ioService.readStringWithPrompt("Please, enter your full name: ");
    }

    private void greeting(List<Question> questionList, String fullName) {
        ioService.printString(fullName + " , welcome to test!");
        ioService.printString("There's " + questionList.size() + " questions for you");
        ioService.printString("You need do do at least " + needSuccessCount + " right answers");
    }

    private void testingQuestions(List<Question> questionList) {
        questionList.forEach(this::testingQuestion);
    }

    private void testingQuestion(Question question) {
        ioService.printString("\nQuestion №" + question.getId() + " - " + question.getTitle());
        ioService.printString(question.getText());
        ioService.printString("Answer options (select one):");
        question.getAnswersValue().forEach(el -> ioService.printString("\t" + el));
        String answer = ioService.readStringWithPrompt("Enter your answer: ");
        ioService.printString(String.valueOf(check(answer, question)));
    }

    private boolean check(String answer, Question question) {
        boolean verdict = question.checkOption(answer);
        if (verdict) {
            successCount++;
        }
        return verdict;
    }

    private void printResult(List<Question> questionList, String fullName) {
        ioService.printString("Result for " + fullName);
        ioService.printString("you've answered at " + successCount + "/" + questionList.size() +
                " questions.");
        ioService.printString("minimum: " + needSuccessCount + " questions");
        ioService.printString(successCount < needSuccessCount ? "Test failed..." : "Test passed!");
    }
}
