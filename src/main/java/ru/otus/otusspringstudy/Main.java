package ru.otus.otusspringstudy;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.otusspringstudy.service.TestService;


public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        var testService = ctx.getBean(TestService.class);
        testService.listQuestions();
    }

}
