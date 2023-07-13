package com.programmers.springmission.customer.domain.enums;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum FindType {

    CUSTOMER_ID("id"),
    CUSTOMER_EMAIL("email");

    private final String inputOption;

    private static final Map<String, FindType> FIND_TYPE_MAP = Arrays.stream(FindType.values())
            .collect(Collectors.toMap(FindType::getInputOption, Function.identity()));

    public static FindType of(String inputOption) {
        return Optional.ofNullable(FIND_TYPE_MAP.get(inputOption))
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.INVALID_CUSTOMER_FIND_TYPE));
    }
}
