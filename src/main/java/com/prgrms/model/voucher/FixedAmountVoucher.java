package com.prgrms.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherPolicy) {
        super(voucherId, discount, voucherPolicy);
    }

    @Override
    public long sale(long price) {
        return price - getVoucherDiscount().getDiscount();
    }
}
