package org.programmers.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucherByType(VoucherType voucherType, UUID voucherId, long value) {
        if (VoucherType.FIXED == voucherType) {
            return new FixedAmountVoucher(voucherId, value);
        } else if (VoucherType.PERCENT == voucherType) {
            return new PercentDiscountVoucher(voucherId, value);
        } else throw new IllegalArgumentException();
    }

}
