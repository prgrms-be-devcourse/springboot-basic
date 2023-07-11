package com.programmers.springbootbasic.service;

import java.util.Arrays;

public enum VoucherType {
    PERCENT("정률 할인"),
    FIX("정액 할인");

    private static final String INVALID_VOUCHER_TYPE = "잘못된 바우처 유형. 현재 입력 값: ";

    private final String description;

    VoucherType(String description) {
        this.description = description;
    }

    public static VoucherType from(String typeDescription) {
        return Arrays.stream(values())
                .filter(enumeration -> typeDescription.equals(enumeration.description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VOUCHER_TYPE + typeDescription));
    }

    public String getDescription() {
        return description;
    }

}
