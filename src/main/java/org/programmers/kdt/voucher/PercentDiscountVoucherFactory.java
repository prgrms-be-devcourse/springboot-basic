package org.programmers.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        Voucher voucher = new PercentDiscountVoucher(voucherId, discount);
        return voucher;
    }
}
