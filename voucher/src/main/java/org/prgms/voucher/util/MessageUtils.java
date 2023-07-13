package org.prgms.voucher.util;


import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageUtils {
    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource source) {
        messageSource = source;
    }

    public static String getMessage(String code) {
        if (messageSource == null) {
            throw new IllegalStateException("MessageSource 인스턴스가 설정되지 않았습니다.");
        }

        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
