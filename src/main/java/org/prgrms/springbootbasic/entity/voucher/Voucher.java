package org.prgrms.springbootbasic.entity.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId = UUID.randomUUID();

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long discount(long beforeDiscount);
}
