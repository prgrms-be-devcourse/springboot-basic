package com.prgrms.management.voucher.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.prgrms.management.config.ErrorMessageType.NOT_EXIST_TYPE;

public enum VoucherType {
    FIXED("할인액") {
        @Override
        public Voucher create(long amount, VoucherType voucherType, UUID customerId) {
            return new FixedVoucher(amount, voucherType, customerId);
        }

        @Override
        public Voucher create(UUID voucherId, LocalDateTime createdAt, long amount, VoucherType voucherType, UUID customerId) {
            return new FixedVoucher(voucherId, createdAt, customerId, amount, voucherType);
        }
    },
    PERCENT("할인율") {
        @Override
        public Voucher create(long amount, VoucherType voucherType, UUID customerId) {
            return new PercentVoucher(amount, voucherType, customerId);
        }

        @Override
        public Voucher create(UUID voucherId, LocalDateTime createdAt, long amount, VoucherType voucherType, UUID customerId) {
            return new PercentVoucher(voucherId, createdAt, customerId, amount, voucherType);
        }
    };

    private final String description;

    VoucherType(String description) {
        this.description = description;
    }

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + NOT_EXIST_TYPE.getMessage()));
    }

    public abstract Voucher create(long amount, VoucherType voucherType, UUID customerId);

    public abstract Voucher create(UUID voucherId, LocalDateTime createdAt, long amount, VoucherType voucherType, UUID customerId);

    public String getDescription() {
        return description;
    }
}
