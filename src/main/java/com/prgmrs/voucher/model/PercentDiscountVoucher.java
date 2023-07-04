package com.prgmrs.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 1-100");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getPercent() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (beforeDiscount * percent) / 100;
    }
}
