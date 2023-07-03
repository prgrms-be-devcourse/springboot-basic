package org.promgrammers.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {


    public PercentDiscountVoucher(long amount, UUID id) {
        super(amount, id);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PercentDiscount;
    }

    @Override
    public long calculateDiscount(long price) {
        return price * (100 - getAmount()) / 100;
    }
}
