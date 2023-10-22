package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

@Getter
public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(long amount) {
        super(amount);
        if (amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Percent discount amount should be between 0 and 100");
        }
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT_DISCOUNT;
    }
}
