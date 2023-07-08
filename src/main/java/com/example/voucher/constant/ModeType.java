package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;

import java.util.Arrays;

public enum ModeType {

    EXIT,
    CREATE,
    LIST,
    NONE;

    public static ModeType getModeType(String typeName) {
        return Arrays.stream(values())
            .filter(m -> m.name().equalsIgnoreCase(typeName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));
    }

}

