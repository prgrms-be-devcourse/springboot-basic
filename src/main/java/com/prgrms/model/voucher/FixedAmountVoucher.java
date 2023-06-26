package com.prgrms.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, Discount discount, VoucherPolicy voucherType) {
        super(voucherId, discount, voucherType);
    }

    @Override
    public long sale(long firstPrice) {
        return firstPrice - getVoucherDiscount().getDiscount();
    }
}
