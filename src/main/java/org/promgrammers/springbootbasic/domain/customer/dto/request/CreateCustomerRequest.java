package org.promgrammers.springbootbasic.domain.customer.dto.request;

import org.promgrammers.springbootbasic.global.error.exception.DataValidationException;

import java.util.regex.Pattern;

import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.INVALID_USERNAME_MESSAGE;

public record CreateCustomerRequest(String username) {

    private static final Pattern USERNAME_REGEX_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]+$");

    public CreateCustomerRequest {
        validateUsername(username);
    }

    private void validateUsername(String username) {
        if (!USERNAME_REGEX_PATTERN.matcher(username).matches()) {
            throw new DataValidationException(INVALID_USERNAME_MESSAGE);
        }
    }
}
