package com.programmers.vouchermanagement.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {
    public static Voucher create(VoucherType type, Long amount) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(amount);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(amount);
            default ->  throw new IllegalArgumentException("Unknown VoucherType: " + type);
        };
    }

    public static Voucher create(UUID id, VoucherType type, Long amount, LocalDateTime createdAt) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(id, amount, createdAt);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, amount, createdAt);
            default ->  throw new IllegalArgumentException("Unknown VoucherType: " + type);
        };
    }
}
