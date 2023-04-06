package ru.otus.otusspringstudy;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.otusspringstudy.service.TestService;

@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext();
        var testService = ctx.getBean(TestService.class);
        testService.listQuestions();
    }

}
