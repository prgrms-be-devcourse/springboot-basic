package org.programmers.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(long discount) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
        return voucher;
    }
}
