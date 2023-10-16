package com.example.homework03;

import com.example.homework03.service.TestingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        //ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestingService service = context.getBean(TestingService.class);
        service.testing();
    }
}