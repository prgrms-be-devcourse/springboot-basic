package com.programmers.domain;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FixedAmountVoucher("1", "fixedamountvoucher"),
    PercentDiscountVoucher("2", "percentdiscountvoucher");

    String number;
    String name;

    VoucherType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static VoucherType findVoucherTypeByNumber(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.number, input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static VoucherType findVoucherTypeByName(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.name, input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
