package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.DiscountType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, DiscountType discountType, int discountValue) {
        super(voucherId, discountType, discountValue);
    }

    @Override
    public long calculateDiscountPrice(long beforePrice) {
        return beforePrice - this.discountValue;
    }
}
