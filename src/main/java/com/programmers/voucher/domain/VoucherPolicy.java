package com.programmers.voucher.domain;

import com.programmers.global.exception.VoucherCommandException;

import java.util.Arrays;

public enum VoucherPolicy {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private final String command;

    VoucherPolicy(String command) {
        this.command = command;
    }

    public static VoucherPolicy of(String command) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(voucherPolicy -> voucherPolicy.command.equals(command))
                .findFirst()
                .orElseThrow(VoucherCommandException::new);
    }
}
