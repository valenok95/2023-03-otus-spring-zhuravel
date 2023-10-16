package com.example.homework03.configuration;

import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "app")
public class TestServiceProperties implements LocaleProvider, ResourceProvider {
    private final Resource resource;
    private final Locale locale;
    private final int needCountOfQuestionForSuccess;

    @ConstructorBinding
    public TestServiceProperties(Locale locale, int needCountOfQuestionForSuccess, Resource resource) {
        this.resource = resource;
        this.needCountOfQuestionForSuccess = needCountOfQuestionForSuccess;
        this.locale = locale;
    }


    @Override
    public Locale getLocale() {
        return locale;
    }

    public int getNeedCountOfQuestionForSuccess() {
        return needCountOfQuestionForSuccess;
    }

    @Override
    public Resource getResource() {
        return resource;
    }
}
