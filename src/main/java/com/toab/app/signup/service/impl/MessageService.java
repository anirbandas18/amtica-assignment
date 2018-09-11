package com.toab.app.signup.service.impl;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private MessageSource messageSource;
	
	private MessageSourceAccessor accessor;

	@PostConstruct
    private void init() {
    	Locale locale = LocaleContextHolder.getLocale();
        this.accessor = new MessageSourceAccessor(messageSource, locale);
    }
	
	public String getMessage(String key, Object[] args) {
		String template = this.accessor.getMessage(key);
		String message = String.format(template, args);
		return message;
	}

}
