package com.programmers.voucher.voucher;

import java.util.Arrays;

import static com.programmers.voucher.menu.Message.VOUCHER_INPUT_ERROR_MESSAGE;


public enum VoucherType {
    FixedAmount("F"),

    PercentDiscount("P"),
    ;

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType getValidateVoucherType(String inputType) {
        return Arrays.stream(values())
                .filter(voucherType ->
                        voucherType.getType().equals(inputType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage()));
    }

    public String getType() {
        return type;
    }
}
