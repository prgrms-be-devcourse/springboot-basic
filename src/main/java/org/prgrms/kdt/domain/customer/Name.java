package org.prgrms.kdt.domain.customer;

import org.prgrms.kdt.exception.Message;
import org.prgrms.kdt.exception.ValidationException;

import java.util.regex.Pattern;

public class Name {
    private static final String PATTERN = "^[가-힣]{2,4}$";

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void validate(String name) {
        if (!Pattern.matches(PATTERN, name)) {
            throw new ValidationException(Message.WRONG_FORMAT_MESSAGE);
        }
    }
}
