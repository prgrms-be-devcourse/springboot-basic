package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;

public enum ServiceType {

    EXIT,
    VOUCHER;

    public static ServiceType getServiceType(String serviceType) {

        return Arrays.stream(ServiceType.values())
            .filter(v -> v.name().equalsIgnoreCase(serviceType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_SERVICE_TYPE_SELECTION));

    }
}
