package com.programmers.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        validateZeroDiscount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    private void validateZeroDiscount(long discountAmount) {
        if(discountAmount == 0) {
            throw new IllegalArgumentException("할인금액 0원은 불가합니다.");
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t$%,d", VoucherType.FIXED_AMOUNT_VOUCHER, voucherId, discountAmount);
    }
}
