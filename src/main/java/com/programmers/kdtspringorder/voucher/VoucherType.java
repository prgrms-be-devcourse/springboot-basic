package com.programmers.kdtspringorder.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static boolean isValidType(String type) {
        return Arrays.stream(values())
                .anyMatch(c -> c.type.equalsIgnoreCase(type));
    }
}
