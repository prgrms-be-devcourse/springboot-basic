package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher create(VoucherType type, UUID id, Long amount) {
        return switch (type) {
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, amount);
            default -> new FixedAmountVoucher(id, amount);
        };
    }
}
