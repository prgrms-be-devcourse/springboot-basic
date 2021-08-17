package com.prgrms.devkdtorder;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher create(VoucherType type, UUID voucherId, long value) {
        if (type == VoucherType.FiXEDAMOUNT) return new FixedAmountVoucher(voucherId, value);
        return new PercentDiscountVoucher(voucherId,value);
    }
}
