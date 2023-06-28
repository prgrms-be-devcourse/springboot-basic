package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

// enum && using enum
public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId) {
        super(voucherId);
    }

    @Override
    public String getVoucherType() {
        return VoucherType.PERCENT.getName();
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }
}
