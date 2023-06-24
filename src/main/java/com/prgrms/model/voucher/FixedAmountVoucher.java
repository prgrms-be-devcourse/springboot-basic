package com.prgrms.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, long amount, String voucherType) {
        super(voucherId,amount,voucherType);
    }
    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - getVoucherDiscount();
    }
}
