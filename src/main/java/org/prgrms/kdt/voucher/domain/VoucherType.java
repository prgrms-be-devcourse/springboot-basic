package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.util.Menu;

import java.util.Arrays;

public enum VoucherType {
    FIXED(1),
    PERCENT(2);

    private final int number;

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
