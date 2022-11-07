package org.prgrms.kdt.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getDiscountValue() {
        return Long.toString(discountAmount);
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
