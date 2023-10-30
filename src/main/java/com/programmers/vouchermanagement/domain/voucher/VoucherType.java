package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.infra.io.ConsoleOutput;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT(1, "Fixed amount Voucher"), PERCENT_DISCOUNT(2, "Percent discount Voucher");

    private final int id;
    private final String description;

    VoucherType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private boolean isSameId(int id) {
        return this.id == id;
    }

    public static void printAllDescriptionsToConsole() {
        for (VoucherType vt : VoucherType.values()) {
            ConsoleOutput.println((vt.ordinal() + 1) + ". " + vt.description);
        }
    }

    public static VoucherType select(int id) {
        return Arrays.stream(values())
                .filter(vt -> vt.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid voucher type id"));
    }
}

