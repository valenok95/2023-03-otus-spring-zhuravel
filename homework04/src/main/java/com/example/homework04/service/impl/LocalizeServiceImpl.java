package com.example.homework04.service.impl;

import com.example.homework04.configuration.LocaleProvider;
import com.example.homework04.service.LocalizeService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class LocalizeServiceImpl implements LocalizeService {
    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public LocalizeServiceImpl(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
        var ms = new ResourceBundleMessageSource();
        ms.setDefaultLocale(localeProvider.getLocale());
        ms.setUseCodeAsDefaultMessage(true);
        ms.setBasename("i18n/appmessages");
        this.messageSource = ms;
    }

    @Override
    public String getMessage(String code, Object... args) { // Метод для получения сообщения по коду
        return this.messageSource.getMessage(code, args, localeProvider.getLocale());
    }
}
