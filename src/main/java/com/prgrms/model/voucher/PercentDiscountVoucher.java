package com.prgrms.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long amount, String voucherType) {
        super(voucherId, amount, voucherType);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (getVoucherDiscount() / 100);
    }
}
