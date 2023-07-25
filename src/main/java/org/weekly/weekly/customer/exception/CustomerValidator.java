package org.weekly.weekly.customer.exception;

import org.weekly.weekly.global.handler.ExceptionCode;

import java.util.regex.Pattern;

public class CustomerValidator {

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";

    private CustomerValidator() {
        throw new CustomerException(ExceptionCode.UTIL_CLASS);
    }

    public static void validateEmailFormat(String email) {
        if (!Pattern.matches(EMAIL_FORMAT, email)) {
            throw new CustomerException(ExceptionCode.NOT_EMAIL_FORMAT);
        }
    }
}
