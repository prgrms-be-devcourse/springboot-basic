package org.prgrms.springbootbasic.factory;

import org.prgrms.springbootbasic.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.voucher.Voucher;

public class PercentAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(long factor) {
        return new PercentAmountVoucher(factor);
    }
}
