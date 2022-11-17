package com.example.springbootbasic.domain.voucher;

import java.util.Arrays;

import static com.example.springbootbasic.exception.voucher.VoucherExceptionMessage.NULL_VOUCHER_TYPE_EXCEPTION;

public enum VoucherType {
    FIXED_AMOUNT("fixed"),
    PERCENT_DISCOUNT("percent");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public static VoucherType findVoucherType(String findVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.voucherType.equals(findVoucherType) || type.name().equals(findVoucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NULL_VOUCHER_TYPE_EXCEPTION.getMessage()));
    }
}
