package com.programmers.voucher.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher"), PERCENT(2, "PercentDiscountVoucher");

    private final int number;
    private final String stringValue;

    VoucherType(int number, String stringValue) {
        this.number = number;
        this.stringValue = stringValue;
    }

    public static Optional<VoucherType> decideVoucherType(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.number == number)
                .findAny();
    }

    public static Optional<VoucherType> decideVoucherType(String stringValue) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.stringValue.equals(stringValue))
                .findAny();
    }
}

