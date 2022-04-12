package org.prgrms.kdt.model;


import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private UUID voucherId;
    private long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException("discountAmount should be over 0");
        }
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return this.discountAmount;
    }
}
