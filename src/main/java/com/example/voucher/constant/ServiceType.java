package com.example.voucher.constant;

import java.util.Arrays;

public enum ServiceType {

    EXIT,
    Voucher;

    public static ServiceType getServiceType(String inputTypeName) {
        return Arrays.stream(ServiceType.values())
            .filter(v -> v.name().equalsIgnoreCase(inputTypeName))
            .findFirst()
            .orElseThrow();
    }
}
