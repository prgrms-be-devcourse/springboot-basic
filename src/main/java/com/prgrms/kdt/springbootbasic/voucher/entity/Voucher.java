package com.prgrms.kdt.springbootbasic.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected long amount;
    protected final LocalDateTime createdAt;
    protected String voucherType;

    public Voucher(UUID voucherId, LocalDateTime createdAt){
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public abstract UUID getVoucherId();
    public abstract long getDiscountedMoney(long beforeDiscount);
    public abstract long getDiscountAmount();
    public abstract LocalDateTime getCreatedAt();
    public abstract String getVoucherType();
    public abstract long setAmount(long amount);
}
