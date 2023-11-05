package com.prgrms.vouchermanagement.core.voucher.domain;

import java.util.Arrays;

public enum VoucherType {

    FIXED("fixed"),
    RATE("rate");

    private String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherType getType(String value) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
