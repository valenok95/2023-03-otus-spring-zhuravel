package ru.otus.otusspringstudy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.otusspringstudy.dao.QuestionDao;
import ru.otus.otusspringstudy.dao.QuestionDaoSimple;

@Configuration
public class QuestionDaoConfig {
    private final String path;

    public QuestionDaoConfig(@Value("${csv-source}") String path) {
        this.path = path;
    }

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoSimple(path);
    }
}
