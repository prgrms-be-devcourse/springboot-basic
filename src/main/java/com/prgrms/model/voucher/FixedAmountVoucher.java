package com.prgrms.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherPolicy) {
        super(voucherId, discount, voucherPolicy);
    }

    @Override
    public double sale(long price) {
        return getVoucherDiscount().getDiscount();
    }
}
