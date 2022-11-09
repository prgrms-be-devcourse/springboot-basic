package org.prgrms.springbootbasic.factory;

import org.prgrms.springbootbasic.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.voucher.Voucher;

public class FixedAmountVoucherFactory implements VoucherFactory {

    @Override
    public Voucher createVoucher(long factor) {
        return new FixedAmountVoucher(factor);
    }
}
