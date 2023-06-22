package com.programmers.domain.voucher;

import com.programmers.console.exception.VoucherCommandException;

import java.util.Arrays;

public enum VoucherPolicy {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private final String command;

    VoucherPolicy(String command) {
        this.command = command;
    }

    public static VoucherPolicy findByCommand(String command) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(voucherPolicy -> voucherPolicy.command.equals(command))
                .findFirst()
                .orElseThrow(VoucherCommandException::new);
    }
}
