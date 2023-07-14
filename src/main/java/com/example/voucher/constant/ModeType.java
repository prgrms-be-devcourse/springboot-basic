package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;

import java.util.Arrays;

public enum ModeType {

    EXIT,
    CREATE,
    LIST,
    DELETE_ALL,
    SEARCH,
    UPDATE;

    public static ModeType getModeType(String inputTypeName) {
        return Arrays.stream(values())
            .filter(m -> m.name().equalsIgnoreCase(inputTypeName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));
    }

}

