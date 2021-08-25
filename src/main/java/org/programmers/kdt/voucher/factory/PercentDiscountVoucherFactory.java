package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PercentDiscountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long discount) {
        return new PercentDiscountVoucher(voucherId, discount);
    }
}
