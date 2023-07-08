package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT_DISCOUNT(1),
    PERCENT_DISCOUNT(2);

    private final int inputNum;

    VoucherType(int inputNum) {
        this.inputNum = inputNum;
    }

    public int getInputNum() {
        return inputNum;
    }

    public static VoucherType getVouchersType(int readVoucherType) {
        return Arrays.stream(values())
            .filter(v -> readVoucherType == v.getInputNum())
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_CANT_CREATE));
    }

}
