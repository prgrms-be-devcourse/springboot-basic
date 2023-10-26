package com.weeklyMission.voucher.domain;

public abstract class Voucher {

    Long voucherId;

    Long amount;

    public abstract Long getVoucherId();

    public abstract Long getAmount();

    public abstract Long discount(long beforeDiscount);
}
