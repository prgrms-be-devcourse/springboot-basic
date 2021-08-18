package org.prgrms.orderapp.model;

import java.io.Serializable;
import java.util.UUID;

public class FixAmountVoucher implements Voucher, Serializable{
    private final UUID voucherId;
    private final long amount;

    public FixAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
