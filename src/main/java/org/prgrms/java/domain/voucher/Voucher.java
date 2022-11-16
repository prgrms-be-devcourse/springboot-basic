package org.prgrms.java.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;

    public Voucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return this.voucherId;
    }

    public abstract long discount(long beforeDiscount);

    @Override
    public abstract String toString();
}
