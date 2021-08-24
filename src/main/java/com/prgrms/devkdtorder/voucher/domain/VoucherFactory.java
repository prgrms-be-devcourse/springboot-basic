package com.prgrms.devkdtorder.voucher.domain;

import com.prgrms.devkdtorder.voucher.domain.FixedAmountVoucher;
import com.prgrms.devkdtorder.voucher.domain.PercentDiscountVoucher;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;

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
