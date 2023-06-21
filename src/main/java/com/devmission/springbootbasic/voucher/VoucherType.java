package com.devmission.springbootbasic.voucher;

import java.util.Arrays;

public enum VoucherType {

    FIXED,
    PERCENT;

    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "선택할 수 없는 타입입니다.";

    public static VoucherType from(String name) {
        return Arrays.stream(values()).filter(voucherType -> voucherType.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE));
    }

}
