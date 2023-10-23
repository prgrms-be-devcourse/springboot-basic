package com.programmers.springbootbasic.domain.customer.vo;

import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email from(String value) {
        if (!Pattern.matches(EMAIL_REGEX, value)) {
            throw new IllegalArgumentException(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
        }
        return new Email(value);
    }
}
