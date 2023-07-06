package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.model.vo.Percent;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final Percent percent;

    public PercentDiscountVoucher(UUID voucherId, Percent percent) {
        if (percent.getValue() <= 0 || percent.getValue() > 100) {
            throw new IllegalArgumentException("percent must be between 1-100");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public Percent getPercent() {
        return percent;
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        return new DiscountValue((beforeDiscount.getValue() * percent.getValue()) / 100);
    }
}
