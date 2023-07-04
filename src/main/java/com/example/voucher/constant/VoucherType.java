package com.example.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherType {

    FIXED_AMOUNT_DISCOUNT(1),
    PERCNET_DISCOUNT(2);

    private final Integer inputNum;

    VoucherType(Integer inputNum) {
        this.inputNum = inputNum;
    }

    public Integer getInputNum() {
        return inputNum;
    }

    public static VoucherType getVouchersType(Integer readVoucherType) {
        VoucherType.valueOf()
        return Arrays.stream(VoucherType.values())
            .filter(e -> readVoucherType == e.getInputNum())
            .findAny()
            .orElseThrow(
                () -> new NoSuchElementException(ConstantStrings.MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT));
    }

}
