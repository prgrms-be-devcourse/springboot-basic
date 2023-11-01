package org.programmers.springorder.model.voucher;

import java.util.Arrays;
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

    private boolean isEqual(String voucherNum) {
        return this.voucherNum.equals(voucherNum);
    }

    public static VoucherType selectVoucherType(String voucherNum) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.isEqual(voucherNum))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 값입니다. 다시 입력해주세요."));
    }

    public long calculate(long beforeDiscount, long discount) {
        return this.expression.applyAsLong(beforeDiscount, discount);
    }

    private boolean isValidDiscountValue(long discountValue) {
        return discountValue >= minimumValue && discountValue <= maximumValue;
    }

    public static void validateDiscountRange(VoucherType voucherType, long discountValue) {
        if (!voucherType.isValidDiscountValue(discountValue)) {
            throw new IllegalArgumentException(String.format("잘못된 입력 값입니다. %d ~ %d 사이의 값을 입력해주세요.", voucherType.minimumValue, voucherType.maximumValue));
        }
    }
}