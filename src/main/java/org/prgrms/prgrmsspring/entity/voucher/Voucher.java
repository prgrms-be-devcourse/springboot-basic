package org.prgrms.prgrmsspring.entity.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;
    protected final String type;

    protected Voucher(UUID voucherId, long amount, String type) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    protected abstract long discount(long beforeDiscount);
}
