package com.devcourse.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final String NOT_SUPPORT_TYPE = "[Error] Your Input Is Not Support Type : ";

    public static VoucherType from(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> isSameType(type.name(), input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORT_TYPE + input));
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    private static boolean isSameType(String type, String input) {
        return type.equals(input.toLowerCase());
    }
}
