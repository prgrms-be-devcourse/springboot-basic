package org.prgms.springbootbasic.domain;

import java.util.UUID;

public abstract class Voucher {
    protected UUID uuid;
    protected VoucherType voucherType;
    protected long amount;

     Voucher(UUID uuid, VoucherType voucherType, long amount) {
        this.uuid = uuid;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public abstract long discount(long beforeAmount);

    public abstract boolean isValidAmount(long amount);


    public UUID getUuid() {
        return uuid;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
