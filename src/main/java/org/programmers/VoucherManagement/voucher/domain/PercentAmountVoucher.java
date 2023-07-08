package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
        super(voucherId, discountType, discountValue);
    }

    @Override
    public DiscountType getDiscountType() {
        return discountType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public DiscountValue getDiscountValue() {
        return discountValue;
    }

}
