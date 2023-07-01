package com.prgrms.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final double PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

    @Override
    public double sale(long price) {
        return price * (getVoucherDiscount().getDiscount() / PERCENT);
    }
}
