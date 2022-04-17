package org.prgrms.springbootbasic.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    private final UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
