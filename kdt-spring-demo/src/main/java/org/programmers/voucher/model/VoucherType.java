package org.programmers.voucher.model;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private String inputVoucherType;

    VoucherType(String inputVoucherType) {
        this.inputVoucherType = inputVoucherType;
    }

    public static VoucherType getInputType(String inputVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.inputVoucherType.equals(inputVoucherType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong VoucherType"));
    }
}