package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;

public enum VoucherType {
    FIXED(1, "name"),
    PERCENT(2, "name");

    private final int number;
    private final String name;

    VoucherType(int number) {
        this.number = number;
    }

    public static VoucherType getType(String str){
        int curNumber = Integer.parseInt(str);
        return Arrays.stream(VoucherType.values())
                .filter((e) -> e.number == curNumber)
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }
}
