package org.prgms.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final long discountAmount;
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId) {
        this.discountAmount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }
}
