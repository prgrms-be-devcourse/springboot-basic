package org.voucherProject.voucherProject.voucher.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED(1) {
        @Override
        public Voucher createVoucher(long amount, UUID customerId) {
            return new FixedAmountVoucher(UUID.randomUUID(), amount, customerId);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
            return new FixedAmountVoucher(voucherId, amount, voucherStatus, createdAt, customerId);
        }
    },
    PERCENT(2) {
        @Override
        public Voucher createVoucher(long amount, UUID customerId) {
            return new PercentDiscountVoucher(UUID.randomUUID(), amount, customerId);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
            return new PercentDiscountVoucher(voucherId, amount, voucherStatus, createdAt, customerId);
        }
    };

    private final int number;

    VoucherType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public abstract Voucher createVoucher(long amount, UUID customerId);

    public abstract Voucher createVoucher(UUID voucherId, long amount, VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId);

    public static VoucherType getVoucherType(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.equals(voucherTypeString.toUpperCase()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static VoucherType getVoucherType(int inputVoucherTypeInt) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.getNumber() == inputVoucherTypeInt)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

