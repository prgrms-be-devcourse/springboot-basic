package com.waterfogsw.voucher.console;

import java.util.Arrays;

import static com.waterfogsw.voucher.console.Messages.INVALID_TYPE;

public enum InputVoucherType {
    FIXED_AMOUNT,
    PERCENT_DISCOUNT;

    public static InputVoucherType from(int num) {
        return Arrays.stream(InputVoucherType.values())
                .filter((i) -> i.ordinal() == num - 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
    }
}
