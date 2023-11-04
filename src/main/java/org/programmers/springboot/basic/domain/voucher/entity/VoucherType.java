package org.programmers.springboot.basic.domain.voucher.entity;

import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;

import java.util.Arrays;

public enum VoucherType {

    FIXED(1),
    PERCENT(2);

    private final int value;

    VoucherType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static VoucherType valueOf(int value) {

        return Arrays.stream(values())
                .filter(voucher -> voucher.getValue() == value)
                .findAny()
                .orElseThrow(VoucherNotFoundException::new);
    }
}
