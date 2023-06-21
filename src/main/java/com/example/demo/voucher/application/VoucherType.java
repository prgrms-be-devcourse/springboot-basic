package com.example.demo.voucher.application;

import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", "Fixed Amount Voucher", "Enter the fixed amount for the voucher:") {
        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new FixedAmountVoucher(id, value);
        }
    },
    PERCENT_DISCOUNT_VOUCHER("2", "Percent Discount Voucher", "Enter the percent discount for the voucher:") {
        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new PercentDiscountVoucher(id, value);
        }
    };

    private final String counter;
    private final String name;
    private final String promptMessage;

    private static final Map<String, VoucherType> counterToVoucherType = new HashMap<>();

    static {
        for (VoucherType type : VoucherType.values()) {
            counterToVoucherType.put(type.counter, type);
        }
    }

    VoucherType(String counter, String name, String promptMessage) {
        this.counter = counter;
        this.name = name;
        this.promptMessage = promptMessage;
    }

    public abstract Voucher createVoucher(UUID id, long value);

    public String getCounter() {
        return this.counter;
    }

    public String getName() {
        return this.name;
    }

    public String getPromptMessage() {
        return this.promptMessage;
    }

    public static Optional<VoucherType> fromCounter(String counter) {
        return Optional.ofNullable(counterToVoucherType.get(counter));
    }
}
