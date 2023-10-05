package com.example.homework03.configuration;

import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "app")
public class TestServiceProperties {
    private final Resource messageSource;
    private final Locale locale;
    private final int needCountOfQuestionForSuccess;

    @ConstructorBinding
    public TestServiceProperties(Locale locale, int needCountOfQuestionForSuccess, Resource messageSource) {
        this.messageSource = messageSource;
        this.needCountOfQuestionForSuccess = needCountOfQuestionForSuccess;
        this.locale = locale;
    }

    public Resource getMessageSource() {
        return messageSource;
    }

    public Locale getLocale() {
        return locale;
    }

    public int getNeedCountOfQuestionForSuccess() {
        return needCountOfQuestionForSuccess;
    }
}
