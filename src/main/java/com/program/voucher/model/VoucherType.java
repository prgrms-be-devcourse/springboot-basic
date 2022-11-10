package com.program.voucher.model;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_DISCOUNT("1","Fixed_amount") ,
    PERCENT_DISCOUNT("2","Percent_discount") ;


    private final String inputType;
    private final String stringType;

    VoucherType(final String inputType, String stringType) {
        this.inputType = inputType;
        this.stringType = stringType;
    }

    public static VoucherType of(final String symbol) {
        return Arrays.stream(values())
                .filter(type -> type.inputType.equals(symbol) || type.stringType.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! Wrong type input. Please enter only given number"));
    }

}
