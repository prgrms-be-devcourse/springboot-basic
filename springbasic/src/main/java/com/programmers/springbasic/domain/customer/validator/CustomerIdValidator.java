package com.programmers.springbasic.domain.customer.validator;

import lombok.Getter;

@Getter
public class CustomerIdValidator {
    private static final String VALID_CUSTOMER_ID_REGEXP = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    private static final String INVALID_CUSTOMER_ID_MESSAGE = "숫자만 입력 가능합니다.";

    private String inputCustomerId;

    public CustomerIdValidator(String inputCustomerId) {
        if (!inputCustomerId.matches(VALID_CUSTOMER_ID_REGEXP)) {
            throw new IllegalArgumentException(INVALID_CUSTOMER_ID_MESSAGE);
        }

        this.inputCustomerId = inputCustomerId;
    }
}
