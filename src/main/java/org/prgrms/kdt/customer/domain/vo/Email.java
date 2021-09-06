package org.prgrms.kdt.customer.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class Email {

    private static final String EMAIL_VALIDATOR = "^[_a-zA-Z0-9-\\+]+(\\.[_a-zA-Z0-9-]+)*@" + "[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,3})$";

    private String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void validateEmail(String email) {
        if (!StringUtils.hasText(email) || !Pattern.matches(EMAIL_VALIDATOR, email)) {
            throw new InvalidArgumentException(ErrorMessage.EMAIL);
        }
    }

    @Override
    public String toString() {
        return email;
    }

}
