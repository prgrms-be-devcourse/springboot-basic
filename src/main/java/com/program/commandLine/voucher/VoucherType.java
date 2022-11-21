package com.program.commandLine.voucher;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED_AMOUNT_DISCOUNT("1", "Fixed_amount"),
    PERCENT_DISCOUNT("2", "Percent_discount");


    private final String numberType;
    private final String stringType;

    VoucherType(final String numberType, String stringType) {
        this.numberType = numberType;
        this.stringType = stringType;
    }

    public static VoucherType getType(final String symbol) {
        return Arrays.stream(values())
                .filter(type -> Objects.equals(type.numberType, symbol) || Objects.equals(type.stringType, symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! 잘못된 입력입니다. 생성할 바우처 타입을 숫자로 입력해주세요"));
    }

    public String getString() {
        return stringType;
    }

}
