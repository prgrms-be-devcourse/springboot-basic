package com.prgrms.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherType) {
        super(voucherId, discount, voucherType);
    }

    @Override
    public long sale(long beforeDiscount) {
        return beforeDiscount * (getVoucherDiscount().getDiscount()/ PERCENT);
    }
}
