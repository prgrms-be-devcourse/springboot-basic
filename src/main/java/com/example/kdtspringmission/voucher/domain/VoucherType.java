package com.example.kdtspringmission.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    FIX("FixedAmountVoucher"),
    RATE("RateAmountVoucher");

    private final String name;

    VoucherType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VoucherType getType(String name) {

        return Arrays.stream(VoucherType.values())
            .filter(value -> value.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No Type"));
    }
}
