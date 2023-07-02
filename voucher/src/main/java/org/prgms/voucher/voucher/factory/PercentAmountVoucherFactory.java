package org.prgms.voucher.voucher.factory;

import org.prgms.voucher.voucher.AmountVoucher;
import org.prgms.voucher.voucher.PercentAmountVoucher;

import java.util.UUID;

public class PercentAmountVoucherFactory implements AmountVoucherFactory {
    @Override
    public AmountVoucher createVoucher(UUID id, int amount) {
        return new PercentAmountVoucher(id, amount);
    }
}
