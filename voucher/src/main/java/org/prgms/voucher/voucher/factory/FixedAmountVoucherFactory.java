package org.prgms.voucher.voucher.factory;

import org.prgms.voucher.voucher.AmountVoucher;
import org.prgms.voucher.voucher.FixedAmountVoucher;

public class FixedAmountVoucherFactory implements AmountVoucherFactory {
    @Override
    public AmountVoucher createVoucher(int discountAmount) {
        return new FixedAmountVoucher(discountAmount);
    }
}
