package com.programmers;

import java.util.Arrays;
import java.util.Optional;

public enum TypeOfVoucher {
    FIXED_AMOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2"),
    ERROR_VOUCHER("0");

    private final String type;
    TypeOfVoucher(String type) {
        this.type = type;
    }

    public static TypeOfVoucher getType(String typeNumber) {
        Optional<TypeOfVoucher> optional = Arrays.stream(TypeOfVoucher.values())
                .filter(o -> o.type.equals(typeNumber))
                .findFirst();
        if (optional.isEmpty()) {
            return ERROR_VOUCHER;
        }
        return optional.get();
    }
}
