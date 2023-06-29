package org.promgrammers.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {


    public PercentDiscountVoucher(long amount, UUID id) {
        super(amount, id);
    }

    @Override
    VoucherType getVoucherType() {
        return VoucherType.PercentDiscount;
    }

    @Override
    long calculateDiscount(long price) {
        long discountAmount = price * (100 - getAmount()) / 100;
        return discountAmount;
    }
}
