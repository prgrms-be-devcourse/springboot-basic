package com.prgrms.kdt.springbootbasic.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected long amount;
    protected LocalDateTime createdAt;
    protected String voucherType;


    public abstract UUID getVoucherId();
    public abstract long getDiscountedMoney(long beforeDiscount);
    public abstract long getDiscountAmount();
    public abstract LocalDateTime getCreatedAt();
    public abstract String getVoucherType();
}
