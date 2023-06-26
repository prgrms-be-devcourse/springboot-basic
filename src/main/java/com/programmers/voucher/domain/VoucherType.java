package com.programmers.voucher.domain;

import com.programmers.global.exception.VoucherCommandException;

import java.util.Arrays;

public enum VoucherType {
    FIXED("1"),
    PERCENT("2");

    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType of(String command) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.command.equals(command))
                .findFirst()
                .orElseThrow(VoucherCommandException::new);
    }
}
