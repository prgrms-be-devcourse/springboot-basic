package org.voucherProject.voucherProject.entity.voucher;

import org.springframework.cglib.core.internal.Function;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED(1, amount -> new FixedAmountVoucher(UUID.randomUUID(), amount)){
        @Override
        public Voucher create(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, amount, voucherStatus, createdAt);
        }
    },
    PERCENT(2, amount -> new PercentDiscountVoucher(UUID.randomUUID(), amount)){
        @Override
        public Voucher create(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, amount, voucherStatus, createdAt);
        }
    };

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

    public abstract Voucher create(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt);

    public static VoucherType getVoucherType(int inputVoucherTypeInt) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(v -> v.getNumber() == inputVoucherTypeInt)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return voucherType;
    }
}
