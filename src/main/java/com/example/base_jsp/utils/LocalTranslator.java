package com.example.base_jsp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class LocalTranslator {
    private final MessageSource messageSource;

    @Autowired
    private LocalTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String toLocale(String code, String lang) {
        Locale locale = new Locale(lang);
        return messageSource.getMessage(code, null, locale);
    }

    public String toLocale(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    public String format(String code, Object... msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return String.format(messageSource.getMessage(code, null, locale), msg);
    }

    public String formatLocalizedMessage(String messageKey, String lang, Object... args) {
        String template = toLocale(messageKey, lang);
        return MessageFormat.format(template, args);
    }
}
