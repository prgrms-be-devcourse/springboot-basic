package com.programmers.springmission.view;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.VoucherException;

import java.util.Arrays;

public enum OptionType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private static final OptionType[] OPTION_TYPES = OptionType.values();

    private final String inputOption;

    OptionType(String inputOption) {
        this.inputOption = inputOption;
    }

    public String getInputOption() {
        return inputOption;
    }

    public static OptionType of(String inputOption) {
        return Arrays.stream(OPTION_TYPES)
                .filter(optionType -> optionType.inputOption.equals(inputOption))
                .findFirst()
                .orElseThrow(() -> new VoucherException(ErrorMessage.INVALID_OPTION_TYPE));
    }
}

