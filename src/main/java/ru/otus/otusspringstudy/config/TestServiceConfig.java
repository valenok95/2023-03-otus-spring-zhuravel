package ru.otus.otusspringstudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(QuestionDaoConfig.class)
public class TestServiceConfig {
    
    
}
