package com.prgmrs.voucher.model;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

import java.util.UUID;

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
        if (percent < 1 || percent > 100) {
            throw new WrongRangeFormatException("percent not between 1-100");
        }
        return (beforeDiscount * percent) / 100;
    }
}
