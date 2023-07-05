package org.prgms.voucher.voucher.factory;

import org.prgms.voucher.voucher.AmountVoucher;
import org.prgms.voucher.voucher.PercentAmountVoucher;

public class PercentAmountVoucherFactory implements AmountVoucherFactory {
    @Override
    public AmountVoucher createVoucher(int amount) {
        return new PercentAmountVoucher(amount);
    }
}
