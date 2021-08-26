package org.prgrms.orderApp.domain.voucher.model;

import java.util.UUID;

// Entity
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public  FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getVoucherAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

}
