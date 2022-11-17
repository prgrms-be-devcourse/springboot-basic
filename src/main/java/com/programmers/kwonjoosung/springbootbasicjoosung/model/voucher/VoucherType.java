package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongVoucherTypeException;

import java.util.stream.Stream;

public enum VoucherType {

    FIXED("fixed"),
    PERCENT("percent");

    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType getVoucherType(String input) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.command.equals(input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new WrongVoucherTypeException(input));
    }

}
