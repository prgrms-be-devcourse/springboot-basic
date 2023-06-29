package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId) {
        super(voucherId);
    }

    @Override
    public String getVoucherType() {
        return VoucherType.FIXED.getName();
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount - amount;
    }
}
