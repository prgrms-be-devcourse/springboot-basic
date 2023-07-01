package com.prgrms.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

    @Override
    public double sale(long price) {
        return getVoucherDiscount().getDiscount();
    }
}
