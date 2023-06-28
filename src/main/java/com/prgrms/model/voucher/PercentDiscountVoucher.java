package com.prgrms.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherPolicy) {
        super(voucherId, discount, voucherPolicy);
    }

    @Override
    public long sale(long price) {
        return price * (getVoucherDiscount().getDiscount() / PERCENT);
    }
}
