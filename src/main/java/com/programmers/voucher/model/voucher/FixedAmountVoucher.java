package com.programmers.voucher.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final String ZERO_DISCOUNT = "할인금액 0원은 불가합니다.";

    public FixedAmountVoucher(UUID voucherId, long discountValue) {
        super(voucherId, discountValue);
    }

    @Override
    protected void validateZeroDiscount(long discountValue) {
        if(discountValue == 0) {
            throw new IllegalArgumentException(ZERO_DISCOUNT);
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountValue;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d$", VoucherType.FIXED_AMOUNT_VOUCHER, voucherId, discountValue);
    }
}
