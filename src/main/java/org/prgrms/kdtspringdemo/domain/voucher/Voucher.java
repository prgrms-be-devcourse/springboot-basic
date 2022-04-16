package org.prgrms.kdtspringdemo.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    UUID voucherId;
    long discountAmount;

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public abstract long discount(long beforeDiscount);
}
