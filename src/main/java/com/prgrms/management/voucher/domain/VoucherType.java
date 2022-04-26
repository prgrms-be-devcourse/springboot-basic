package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

public enum VoucherType {
    FIXED("할인액") {
        @Override
        public Voucher create(VoucherType voucherType, Long amount, UUID customerId) {
            return new FixedVoucher(UUID.randomUUID(), LocalDateTime.now(), customerId, amount, voucherType);
        }

        @Override
        public Voucher create(UUID voucherId, LocalDateTime createdAt, VoucherType voucherType, Long amount, UUID customerId) {
            return new FixedVoucher(voucherId, createdAt, customerId, amount, voucherType);
        }
    },
    PERCENT("할인율") {
        @Override
        public Voucher create(VoucherType voucherType, Long amount, UUID customerId) {
            return new PercentVoucher(UUID.randomUUID(), LocalDateTime.now(), customerId, amount, voucherType);
        }

        @Override
        public Voucher create(UUID voucherId, LocalDateTime createdAt, VoucherType voucherType, Long amount, UUID customerId) {
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
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + ErrorMessageType.NOT_EXIST_VOUCHER_TYPE.getMessage()));
    }

    public abstract Voucher create(VoucherType voucherType, Long amount, UUID customerId);

    public abstract Voucher create(UUID voucherId, LocalDateTime createdAt, VoucherType voucherType, Long amount, UUID customerId);

    public String getDescription() {
        return description;
    }
}
