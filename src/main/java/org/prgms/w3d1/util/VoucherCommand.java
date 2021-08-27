package org.prgms.w3d1.util;

import java.util.Arrays;

public enum VoucherCommand {
    FIXED_AMOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2");

    private final String command;

    VoucherCommand(String command){
        this.command = command;
    }

    public static VoucherCommand getCommand(String str) {
        return Arrays.stream(values())
                .filter(v -> v.command.equals(str))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + str));
    }
}

