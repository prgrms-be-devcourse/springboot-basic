package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import java.time.LocalDateTime;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(VoucherType type, PercentAmount amount) {
        super(null, type, amount, null, null);
    }

    public PercentAmountVoucher(Long id, VoucherType type, VoucherAmount amount,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, amount, createdAt, updatedAt);
    }

    @Override
    public double discount(double originAmount) {
        PercentAmount percentAmount = (PercentAmount) getAmount();
        double remainRate = percentAmount.getRemainRate();
        return originAmount * remainRate;
    }
}
