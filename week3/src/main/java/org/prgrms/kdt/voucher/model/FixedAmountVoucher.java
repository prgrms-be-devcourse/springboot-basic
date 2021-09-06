package org.prgrms.kdt.voucher.model;

import lombok.Data;

import java.util.UUID;

@Data
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private UUID walletId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}


