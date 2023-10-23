package org.programmers.springorder.voucher.model;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.function.LongBinaryOperator;

public enum VoucherType {
    FIXED("1", ((beforeDiscount, discount) -> beforeDiscount - discount), 100, 10000),
    PERCENT("2", ((beforeDiscount, discount) -> beforeDiscount * (100 - discount) / 100), 3, 50);

    private final String voucherNum;
    private final LongBinaryOperator expression;
    private final long minimumValue;
    private final long maximumValue;

    VoucherType(String voucherNum, LongBinaryOperator expression, long minimumValue, long maximumValue) {
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
        return this.expression.applyAsLong(beforeDiscount, discount);
    }

    public long getMinimumValue() {
        return minimumValue;
    }

    public long getMaximumValue() {
        return maximumValue;
    }
}