package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;

import java.util.UUID;

public class PercentDiscountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        Voucher voucher = new PercentDiscountVoucher(voucherId, discount);
        return voucher;
    }
}
