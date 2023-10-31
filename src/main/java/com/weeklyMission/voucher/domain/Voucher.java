package com.weeklyMission.voucher.domain;

import java.util.UUID;

public abstract class Voucher {

    String voucherId;

    long amount;

    public abstract String getVoucherId();

    public abstract long getAmount();

    public abstract long discount(long beforeDiscount);
}
