package com.programmers.voucher.domain;

import com.programmers.console.exception.VoucherCommandException;
import com.programmers.voucher.util.TriFunction;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

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
