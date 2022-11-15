package org.prgrms.springbootbasic.factory;

import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("amount > 0");
        }
        return new FixedAmountVoucher(quantity);
    }
}
