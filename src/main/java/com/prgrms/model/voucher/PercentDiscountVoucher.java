package com.prgrms.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final double PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherPolicy) {
        super(voucherId, discount, voucherPolicy);
    }

    @Override
    public double sale(long price) {
        return price - price * (getVoucherDiscount().getDiscount() / PERCENT);
    }
}
