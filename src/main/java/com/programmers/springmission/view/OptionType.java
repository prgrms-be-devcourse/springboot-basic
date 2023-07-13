package com.programmers.springmission.view;

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
public enum OptionType {

    EXIT("exit"),
    VOUCHER("voucher"),
    CUSTOMER("customer");

    private final String inputOption;

    private static final Map<String, OptionType> OPTION_TYPE_MAP = Arrays.stream(OptionType.values())
            .collect(Collectors.toMap(OptionType::getInputOption, Function.identity()));

    public static OptionType of(String inputOption) {
        return Optional.ofNullable(OPTION_TYPE_MAP.get(inputOption))
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.INVALID_OPTION_TYPE));
    }
}

