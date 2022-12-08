package org.prgrms.springbootbasic.entity.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long getQuantity();

    public abstract long discount(long beforeDiscount);
}
