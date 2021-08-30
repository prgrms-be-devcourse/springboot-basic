package org.programmers.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher getVoucherType(String voucherType, UUID voucherId, long value) {
        if ("FixedAmountVoucher".equals(voucherType)) {
            return new FixedAmountVoucher(voucherId, value);
        } else {
            return new PercentDiscountVoucher(voucherId, value);
        }
    }

}
