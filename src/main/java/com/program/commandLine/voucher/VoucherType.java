package com.program.commandLine.voucher;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED_AMOUNT_DISCOUNT("1","Fixed_amount") ,
    PERCENT_DISCOUNT("2","Percent_discount") ;


    private final String numberType;
    private final String stringType;

    VoucherType(final String numberType, String stringType) {
        this.numberType = numberType;
        this.stringType = stringType;
    }

    public static VoucherType of(final String symbol) {
        return Arrays.stream(values())
                .filter(type -> Objects.equals(type.numberType,symbol) || Objects.equals(type.stringType,symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! Wrong type input. Please enter only given number"));
    }

}
