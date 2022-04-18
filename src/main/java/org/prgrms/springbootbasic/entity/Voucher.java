package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public boolean isFixed() {
        return false;
    }

    public boolean isPercent() {
        return false;
    }
}
