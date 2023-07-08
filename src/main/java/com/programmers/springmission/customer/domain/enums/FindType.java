package com.programmers.springmission.customer.domain.enums;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;

import java.util.Arrays;

public enum FindType {

    CUSTOMER_ID("id"),
    CUSTOMER_EMAIL("email");

    private static final FindType[] FIND_TYPES = FindType.values();

    private final String inputOption;

    FindType(String inputOption) {
        this.inputOption = inputOption;
    }

    public static FindType of(String inputOption) {
        return Arrays.stream(FIND_TYPES)
                .filter(findType -> findType.inputOption.equals(inputOption))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.INVALID_CUSTOMER_FIND_TYPE));
    }
}
