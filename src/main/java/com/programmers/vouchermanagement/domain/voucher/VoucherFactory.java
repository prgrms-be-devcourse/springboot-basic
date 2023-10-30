package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher create(VoucherType type, Long amount) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(amount);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(amount);
            default -> new FixedAmountVoucher(amount);
        };
    }

    public static Voucher create(UUID id, Long amount, VoucherType type) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(id, amount);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, amount);
            default -> new FixedAmountVoucher(id, amount);
        };
    }
}
