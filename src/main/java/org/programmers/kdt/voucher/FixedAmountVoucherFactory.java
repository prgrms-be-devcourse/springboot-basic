package org.programmers.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(long discount) {
        return new FixedAmountVoucher(UUID.randomUUID(), discount);
    }
}
