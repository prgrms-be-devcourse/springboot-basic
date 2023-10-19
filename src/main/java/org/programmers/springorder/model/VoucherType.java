package org.programmers.springorder.model;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED("1", ((beforeDiscount, discount) -> beforeDiscount - discount), 100, 10000),
    PERCENT("2", ((beforeDiscount, discount) -> beforeDiscount * (100 - discount) / 100), 3, 50);

    private final String voucherNum;
    private final BiFunction<Long, Long, Long> expression;
    private final long minimumValue;
    private final long maximumValue;

    VoucherType(String voucherNum, BiFunction<Long, Long, Long> expression, long minimumValue, long maximumValue) {
        this.voucherNum = voucherNum;
        this.expression = expression;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    public static VoucherType selectVoucherType(String voucherNum) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.voucherNum.equals(voucherNum))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("유효하지 않은 값입니다. 다시 입력해주세요."));
    }

    public long calculate(long beforeDiscount, long discount) {
        return this.expression.apply(beforeDiscount, discount);
    }

    public long getMinimumValue() {
        return minimumValue;
    }

    public long getMaximumValue() {
        return maximumValue;
    }
}