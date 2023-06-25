package com.ray.junho.voucher.domain;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_AMOUNT(2);

    private final int code;

    VoucherType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
