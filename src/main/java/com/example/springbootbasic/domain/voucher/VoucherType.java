package com.example.springbootbasic.domain.voucher;

import java.util.Arrays;

import static com.example.springbootbasic.exception.VoucherException.NULL_VOUCHER_TYPE;

public enum VoucherType {
    FIXED_AMOUNT("fixed"),
    PERCENT_DISCOUNT("percent");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public static VoucherType findVoucherType(String findVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.voucherType.equals(findVoucherType))
                .findFirst()
                .orElseThrow(() -> new NullPointerException(NULL_VOUCHER_TYPE.getMessage()));
    }

    public String getVoucherType() {
        return voucherType;
    }
}
