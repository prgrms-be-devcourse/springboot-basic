package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PercentDiscountVoucher extends Voucher {
    private final long MIN_AMOUNT = 0L;
    private final long MAX_AMOUNT = 100L;

    public PercentDiscountVoucher(long amount) {
        super(VoucherType.PERCENT_DISCOUNT, amount);
        validateAmount(amount);
    }

    public PercentDiscountVoucher(UUID id, long amount) {
        super(id, VoucherType.PERCENT_DISCOUNT, amount);
        validateAmount(amount);
    }

    private void validateAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("Percent discount amount should be between 0 and 100");
        }
    }
}
