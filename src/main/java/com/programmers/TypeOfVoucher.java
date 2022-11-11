package com.programmers;

import java.util.Arrays;

public enum TypeOfVoucher {
    FixedAmountVoucher("1"),
    PercentDiscountVoucher("2");

    private final String type;
    TypeOfVoucher(String type) {
        this.type = type;
    }

    public static TypeOfVoucher getType(String typeNumber) {
        return Arrays.stream(TypeOfVoucher.values())
                .filter(o -> o.type.equals(typeNumber))
                .findFirst()
                .get();
    }
}
