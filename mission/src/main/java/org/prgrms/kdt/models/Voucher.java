package org.prgrms.kdt.models;

import java.util.UUID;

public abstract class Voucher {

    protected UUID voucherId;


    public UUID getVoucherId() {
        return null;
    }

    public void setVoucherId(UUID voucherId) {
        return;
    }

    abstract public long discount();
}
