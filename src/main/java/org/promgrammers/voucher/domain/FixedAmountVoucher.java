package org.promgrammers.voucher.domain;

import java.util.UUID;


public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(long amount, UUID id) {
        super(amount, id);
    }

    @Override
    VoucherType getVoucherType() {
        return VoucherType.FixedAmount;
    }

    @Override
    long calculateDiscount(long price) {
        long discountAmount = price - getAmount();
        return Math.max(discountAmount, 0);
    }
}
