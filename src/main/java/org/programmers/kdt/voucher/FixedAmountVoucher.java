package org.programmers.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforedDiscount) {
        return beforedDiscount - amount;
    }

    @Override
    public String toString() {
        return "<< Fixed Amount Voucher >>\nID : " + this.voucherId + "\nDiscount : $" + this.amount;
    }

    @Override
    public long getDiscount() {
        return this.amount;
    }
}
