package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;

public enum CustomerType {

    NORMAL,
    BLACK;

    public static CustomerType getCustomerType(String inputTypeName) {
        return Arrays.stream(CustomerType.values())
            .filter(v -> v.name().equalsIgnoreCase(inputTypeName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));
    }

}
