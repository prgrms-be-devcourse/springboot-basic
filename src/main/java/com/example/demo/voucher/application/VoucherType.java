package com.example.demo.voucher.application;

import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1") {
        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new FixedAmountVoucher(id, value);
        }
    },
    PERCENT_DISCOUNT_VOUCHER("2") {
        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new PercentDiscountVoucher(id, value);
        }
    };

    private final String counter;

    private static final Map<String, VoucherType> counterToVoucherType = Stream
            .of(values())
            .collect(Collectors.toMap(VoucherType::getCounter, v -> v));

    VoucherType(String counter) {
        this.counter = counter;
    }

    public abstract Voucher createVoucher(UUID id, long value);

    public String getCounter() {
        return this.counter;
    }

    public static Optional<VoucherType> fromCounter(String counter) {
        return Optional.ofNullable(counterToVoucherType.get(counter));
    }
}