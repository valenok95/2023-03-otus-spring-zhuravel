package ru.otus.otusspringstudy;

import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.otusspringstudy.service.TestService;


@ComponentScan
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(Main.class);
        var testService = ctx.getBean(TestService.class);
        testService.performTest();
    }

}
