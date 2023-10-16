package org.prgrms.prgrmsspring.entity.voucher;

import java.util.UUID;


public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + amount +
                '}';
    }
}
