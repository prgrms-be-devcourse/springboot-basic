package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher{

    public PercentAmountVoucher(UUID voucherId, DiscountType discountType, int discountValue) {
        super(voucherId, discountType, discountValue);
    }
    @Override
    public long calculateDiscountPrice(long beforePrice){
        return beforePrice * ((100-this.discountValue) / 100);
    }
}
