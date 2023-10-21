package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final String voucherName;
    protected final float discountAmount;

    protected Voucher(UUID voucherId, String voucherName, float discountAmount) {
        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.discountAmount = discountAmount;
    }

    public abstract UUID getId();

    public abstract float discount(float beforeDiscount);

    public abstract String joinInfo(String separator);
}
