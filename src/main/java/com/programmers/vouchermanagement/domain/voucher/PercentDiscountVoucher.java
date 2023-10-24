package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(UUID id, long amount) {
        super(id, VoucherType.PERCENT_DISCOUNT, amount);
        validateAmount(amount);
    }
    public PercentDiscountVoucher(long amount) {
        super(VoucherType.PERCENT_DISCOUNT, amount);
        validateAmount(amount);
    }

    public static PercentDiscountVoucher fixture() {
        return new PercentDiscountVoucher(10L);
    }

    private void validateAmount(long amount) {
        if (amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Percent discount amount should be between 0 and 100");
        }
    }
}
