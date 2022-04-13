package org.voucherProject.voucherProject.entity.voucher;

import org.springframework.cglib.core.internal.Function;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED(1, amount -> new FixedAmountVoucher(UUID.randomUUID(), amount)),
    PERCENT(2, amount -> new PercentDiscountVoucher(UUID.randomUUID(), amount));

    private final int number;
    private Function<Long, Voucher> createVoucherExpression;

    VoucherType(int number, Function<Long, Voucher> createVoucherExpression) {
        this.number = number;
        this.createVoucherExpression = createVoucherExpression;
    }

    public int getNumber() {
        return number;
    }

    public Voucher createVoucher(long amount) {
        return createVoucherExpression.apply(amount);
    }

    public static VoucherType getVoucherType(int inputVoucherTypeInt) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(v -> v.getNumber() == inputVoucherTypeInt)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return voucherType;
    }
}
