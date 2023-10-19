package com.example.homework03.service.impl;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.service.LocalizeService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class LocalizeServiceImpl implements LocalizeService {
    private final TestServiceProperties props;
    private final MessageSource messageSource;

    public LocalizeServiceImpl(TestServiceProperties props) {
        this.props = props;
        var ms = new ResourceBundleMessageSource();
        ms.setDefaultLocale(props.getLocale());
        ms.setUseCodeAsDefaultMessage(true);
        ms.setBasename("i18n/appmessages");
        this.messageSource = ms;
    }

    @Override
    public String getMessage(String code, Object... args) { // Метод для получения сообщения по коду
        return this.messageSource.getMessage(code, args, props.getLocale());
    }
}
