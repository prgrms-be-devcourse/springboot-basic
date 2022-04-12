package org.prgrms.kdt.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private UUID voucherId;
    private long discountPercent;
    private static final int MAX_DISCOUNT_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        if (discountPercent <= 0) {
            throw new IllegalArgumentException("discountPercent should be over 0");
        }
        if (discountPercent > MAX_DISCOUNT_PERCENT) {
            throw new IllegalArgumentException("Max discountPercent is 100");
        }
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long getDiscountAmount() {
        return discountPercent;
    }
}
