package org.prgms.voucher.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageSourceInitializer {
    private final MessageSource messageSource;

    public MessageSourceInitializer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void initializeMessageSource() {
        MessageUtils.setMessageSource(messageSource);
    }
}
