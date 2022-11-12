package com.program.commandLine.voucher;

import java.util.Arrays;
import java.util.Objects;

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
                .filter(type -> Objects.equals(type.inputType,symbol) || Objects.equals(type.stringType,symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! Wrong type input. Please enter only given number"));
    }

}
