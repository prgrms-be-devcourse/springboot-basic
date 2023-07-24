package org.promgrammers.springbootbasic.domain.customer.dto.request;

import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.global.error.exception.DataValidationException;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.INVALID_USERNAME_MESSAGE;

public record UpdateCustomerRequest(UUID customerId, String username, CustomerType customerType) {

    private static final java.util.regex.Pattern USERNAME_REGEX_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]+$");

    public UpdateCustomerRequest {
        validateUsername(username);
    }

    private void validateUsername(String username) {
        if (!USERNAME_REGEX_PATTERN.matcher(username).matches()) {
            throw new DataValidationException(INVALID_USERNAME_MESSAGE);
        }
    }
}
