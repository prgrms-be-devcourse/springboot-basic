package com.programmers.assignment.voucher.util.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherVariable {
    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String discountWay;

    VoucherVariable(String command) {
        this.discountWay = command;
    }

    @Override
    public String toString() {
        return discountWay;
    }

    public static String chooseDiscountWay(String command) {
        return getVoucher(command).toString();
    }

    public static VoucherVariable getVoucher(String command) {
        return Arrays.stream(values())
            .filter(o -> o.discountWay.equals(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(command + "는 존재하지 않는 바우처 입니다."));
    }
}
