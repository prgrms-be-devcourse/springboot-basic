package org.voucherProject.voucherProject.entity.voucher;

import org.springframework.cglib.core.internal.Function;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED(1) {
        @Override
        public Voucher createVoucher(long amount) {
            return new FixedAmountVoucher(UUID.randomUUID(), amount);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, amount, voucherStatus, createdAt);
        }
    },
    PERCENT(2) {
        @Override
        public Voucher createVoucher(long amount) {
            return new PercentDiscountVoucher(UUID.randomUUID(), amount);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, amount, voucherStatus, createdAt);

        }
    };

    private final int number;

    VoucherType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public abstract Voucher createVoucher(long amount);

    public abstract Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt);

    public static VoucherType getVoucherType(int inputVoucherTypeInt) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(v -> v.getNumber() == inputVoucherTypeInt)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return voucherType;
    }
}

