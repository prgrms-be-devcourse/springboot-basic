package com.zerozae.voucher.common.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {
    private static MessageSource messageSource;

    public MessageConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessage(String message){
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}
