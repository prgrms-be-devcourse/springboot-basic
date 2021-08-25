package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        return new FixedAmountVoucher(voucherId, discount);
    }
}
