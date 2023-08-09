package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;

public enum ServiceType {

    EXIT,
    VOUCHER,
    CUSTOMER,
    WALLET
    ;

    public static ServiceType getServiceType(String inputTypeName) {
        return Arrays.stream(ServiceType.values())
            .filter(v -> v.name().equalsIgnoreCase(inputTypeName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_SERVICE_TYPE_SELECTION));
    }
}
