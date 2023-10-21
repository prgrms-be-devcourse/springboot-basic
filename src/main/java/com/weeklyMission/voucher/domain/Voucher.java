package com.weeklyMission.voucher.domain;

import java.util.UUID;

public abstract class Voucher {

    UUID voucherId;

    long amount;

    public abstract UUID getVoucherId();

    public abstract long getAmount();

    public abstract long discount(long beforeDiscount);
}
