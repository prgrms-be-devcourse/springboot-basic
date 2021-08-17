package com.prgrms.devkdtorder;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher create(VoucherType type, UUID voucherId, long value) {
        if (type == VoucherType.FIXEDAMOUNT) return new FixedAmountVoucher(voucherId, value);
        return new PercentDiscountVoucher(voucherId,value);
    }

    public static Voucher create(VoucherType type, long value) {
        if (type == VoucherType.FIXEDAMOUNT) return new FixedAmountVoucher(UUID.randomUUID(), value);
        return new PercentDiscountVoucher(UUID.randomUUID(),value);
    }
}
