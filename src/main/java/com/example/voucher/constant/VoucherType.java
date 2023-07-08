package com.example.voucher.constant;

import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT_DISCOUNT(1),
    PERCENT_DISCOUNT(2);

    private final Integer inputNum;

    VoucherType(Integer inputNum) {
        this.inputNum = inputNum;
    }

    public Integer getInputNum() {
        return inputNum;
    }

    public static VoucherType getVouchersType(Integer readVoucherType) {
        return Arrays.stream(VoucherType.values())
            .filter(e -> readVoucherType == e.getInputNum())
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException());
    }

}
