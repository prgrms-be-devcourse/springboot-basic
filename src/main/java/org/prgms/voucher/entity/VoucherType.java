package org.prgms.voucher.entity;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2"),
    EMPTY("");

    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType of(String command) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElse(VoucherType.EMPTY);
    }
}
