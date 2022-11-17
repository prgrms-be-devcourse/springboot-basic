package org.prgrms.springbootbasic.factory;

import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

import java.util.UUID;

public class PercentAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(long quantity) {
        if (quantity < 0 || 100 < quantity) {
            throw new IllegalArgumentException("percent range: 0 ~ 100");
        }
        return new PercentAmountVoucher(UUID.randomUUID(), quantity);
    }
}
