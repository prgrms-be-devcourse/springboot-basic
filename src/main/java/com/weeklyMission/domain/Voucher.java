package com.weeklyMission.domain;

import java.util.UUID;

public abstract class Voucher {

    protected final UUID voucherId;

    protected final long amount;

    protected Voucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    UUID getVoucherId(){
        return voucherId;
    }

    abstract long discount(long beforeDiscount);
}
