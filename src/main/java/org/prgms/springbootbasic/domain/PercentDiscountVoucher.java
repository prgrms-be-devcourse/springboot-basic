package org.prgms.springbootbasic.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent > 100 || percent < 0) {
            log.warn("percent value is out of range.");
            throw new IllegalArgumentException("percent value is out of range.");
        }

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * percent / 100L;
    }
}
