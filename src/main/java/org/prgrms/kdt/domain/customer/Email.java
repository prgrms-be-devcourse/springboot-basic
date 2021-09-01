package org.prgrms.kdt.domain.customer;

import org.prgrms.kdt.exception.Message;
import org.prgrms.kdt.exception.ValidationException;

import java.util.regex.Pattern;

public class Email {

    private static final String PATTERN = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;";

    private final String email;

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void validate(String email) {
        if (!Pattern.matches(PATTERN, email)) {
            throw new ValidationException(Message.WRONG_FORMAT_MESSAGE);
        }
    }

}
