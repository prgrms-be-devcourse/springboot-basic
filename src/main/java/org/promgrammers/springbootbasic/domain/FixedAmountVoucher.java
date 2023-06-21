package org.promgrammers.springbootbasic.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long MIN_AMOUNT = 0;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - this.getAmount();
        return discountAmount < 0 ? 0 : discountAmount;
    }

    @Override
    protected void validateAmount(long discountAmount) {
        if (discountAmount < MIN_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 0보다 적을 수 없습니다.");
        }
    }
}
