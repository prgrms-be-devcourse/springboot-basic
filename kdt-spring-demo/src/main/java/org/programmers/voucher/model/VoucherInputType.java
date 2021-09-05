package org.programmers.voucher.model;

import java.util.Arrays;


public enum VoucherInputType {
    EXIT("exit"),
    CREATE("create"),
    VOUCHERLIST("voucherlist"),
    DELETE("delete"),
    FIND("find"),
    BLACKLIST("blacklist"),
    UPDATE("update");

    private String input;

    VoucherInputType(String input) {
        this.input = input;
    }

    public static VoucherInputType getInputType(String input) {
        return Arrays.stream(VoucherInputType.values())
                .filter(voucherInputType -> voucherInputType.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong VoucherMode Input "));

    }
}
