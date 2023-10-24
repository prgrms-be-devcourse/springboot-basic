package org.prgrms.prgrmsspring.entity.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;

    protected Voucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public abstract long discount(long beforeDiscount);
}
