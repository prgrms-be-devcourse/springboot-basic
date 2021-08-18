package org.programmers.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        return new FixedAmountVoucher(voucherId, discount);
    }
}
