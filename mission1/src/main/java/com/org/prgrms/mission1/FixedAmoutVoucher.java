package com.org.prgrms.mission1;

import java.util.UUID;

public class FixedAmoutVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    public FixedAmoutVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FixedAmoutVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public UUID getVoucherID() {
        return voucherId;
    }

    public long discount(long beforeDiscounnt) {
        return beforeDiscounnt - amount;
    }
}
