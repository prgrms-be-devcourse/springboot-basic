package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher create(VoucherType type, Long amount) {
        return switch (type) {
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(amount);
            default -> new FixedAmountVoucher(amount);
        };
    }

    public static Voucher create(UUID id, VoucherType type, Long amount) {
        return switch (type) {
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, amount);
            default -> new FixedAmountVoucher(id, amount);
        };
    }
}
