package com.prgmrs.voucher.domain;

import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Qualifier("percent")
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
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
        return beforeDiscount * (percent / 100);
    }
}
