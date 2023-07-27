package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;

public enum ModeType {

    CREATE,
    LIST,
    DELETE_ALL,
    SEARCH,
    UPDATE,
    DELETE,
    SEARCH_BY_CUSTOMER,
    SEARCH_BY_VOUCHER
    ;

    public static ModeType getModeType(String inputTypeName) {
        return Arrays.stream(values())
            .filter(m -> m.name().equalsIgnoreCase(inputTypeName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));
    }

}

