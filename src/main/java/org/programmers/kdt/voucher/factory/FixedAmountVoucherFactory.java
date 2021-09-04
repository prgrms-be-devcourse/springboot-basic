package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;

import java.util.UUID;

public class FixedAmountVoucherFactory extends VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        return new FixedAmountVoucher(voucherId, discount);
    }
}
