package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import java.time.LocalDateTime;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(VoucherType type, FixedAmount amount) {
        super(null, type, amount, null, null);
    }

    public FixedAmountVoucher(Long id, VoucherType type, FixedAmount amount,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, amount, createdAt, updatedAt);
    }

    @Override
    public double discount(double originAmount) {
        int discountAmount = getAmount().getValue();
        return originAmount - discountAmount;
    }
}
