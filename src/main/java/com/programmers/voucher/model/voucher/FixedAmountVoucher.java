package com.programmers.voucher.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final String WRONG_DISCOUNT = "할인금액 0원 이하는 불가합니다.";

    public FixedAmountVoucher(UUID voucherNumber, long discountValue) {
        super(voucherNumber, discountValue);
    }

    @Override
    protected void validateZeroDiscount(long discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException(WRONG_DISCOUNT);
        }
    }

    @Override
    public long discount(long fullAmount) {
        return fullAmount - discountValue;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d$", VoucherType.FIXED_AMOUNT_VOUCHER, voucherId, discountValue);
    }
}