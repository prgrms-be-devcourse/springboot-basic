package org.programmers.springorder.model;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED(1, ((beforeDiscount, discount) -> beforeDiscount - discount)),
    PERCENT(2, ((beforeDiscount, discount) -> beforeDiscount * (100 - discount) / 100));

    private final int voucherNum;
    private final BiFunction<Long, Long, Long> expression;

    VoucherType(int voucherNum, BiFunction<Long, Long, Long> expression) {
        this.voucherNum = voucherNum;
        this.expression = expression;
    }

    public static VoucherType selectVoucherType(int voucherNum) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.voucherNum == voucherNum)
                .findAny()
                .orElseThrow(() -> new InputMismatchException("유효하지 않은 값입니다. 다시 입력해주세요."));
    }

    public long calculate(long beforeDiscount, long discount) {
        return this.expression.apply(beforeDiscount, discount);
    }
}