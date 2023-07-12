package com.programmers.voucher.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED(1), PERCENT(2);

    private final int number;

    VoucherType(int number) {
        this.number = number;
    }

    public static Optional<VoucherType> decideVoucherType(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.number == number)
                .findAny();
    }
}

