package com.waterfogsw.voucher.console;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_TYPE;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private final int typeNum;

    VoucherType(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public static String getAllInputVoucherType() {
        return Stream.of(VoucherType.values())
                .map((v) -> v.getTypeNum() + " : " + v)
                .collect(Collectors.joining("\n"));
    }


    public static VoucherType from(int num) {
        return Arrays.stream(VoucherType.values())
                .filter((i) -> i.getTypeNum() == num)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
    }
}
