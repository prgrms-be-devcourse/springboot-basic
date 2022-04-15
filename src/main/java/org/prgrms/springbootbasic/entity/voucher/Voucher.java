package org.prgrms.springbootbasic.entity.voucher;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    private final UUID voucherId;
    private UUID customerId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
