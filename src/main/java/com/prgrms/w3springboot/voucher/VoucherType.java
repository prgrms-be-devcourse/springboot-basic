package com.prgrms.w3springboot.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(final String type) {
        return Arrays.stream(values())
                .filter(t -> t.isVoucherType(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find this voucher type :("));
    }

    private boolean isVoucherType(final String type) {
        return this.type.equals(type);
    }
}