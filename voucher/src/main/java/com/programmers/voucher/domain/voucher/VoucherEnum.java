package com.programmers.voucher.domain.voucher;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherEnum {
    FIXED(1), PERCENT(2);

    private final int number;

    VoucherEnum(int number) {
        this.number = number;
    }

    public static Optional<VoucherEnum> decideVoucherType(int number) {
        return Arrays.stream(VoucherEnum.values())
                .filter(v -> v.number == number)
                .findAny();
    }
}

