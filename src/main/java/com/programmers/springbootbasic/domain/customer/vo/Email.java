package com.programmers.springbootbasic.domain.customer.vo;

import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email from(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
        }
        return new Email(value);
    }
}
