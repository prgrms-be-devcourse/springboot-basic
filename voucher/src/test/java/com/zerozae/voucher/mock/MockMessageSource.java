package com.zerozae.voucher.mock;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class MockMessageSource implements MessageSource {
    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return "This is a test message";
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return "This is a test message";
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return "This is a test message";
    }
}
