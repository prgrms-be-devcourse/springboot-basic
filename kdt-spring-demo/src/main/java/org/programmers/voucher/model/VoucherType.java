package org.programmers.voucher.model;

import java.util.Arrays;
import java.util.Locale;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private String inputVoucherType;

    VoucherType(String inputVoucherType) {
        this.inputVoucherType = inputVoucherType;
    }

    public static VoucherType getInputType(String inputVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.inputVoucherType.equals(inputVoucherType.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong VoucherType"));
    }
}