package com.waterfogsw.voucher.console;

import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_TYPE;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private final int num;

    VoucherType(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static VoucherType getVoucherType(int num) {
        return Stream.of(VoucherType.values())
                .filter(i -> i.getNum() == num)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
    }
}
