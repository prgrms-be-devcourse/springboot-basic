package org.prgms.voucher.voucher.factory;

import org.prgms.voucher.voucher.AmountVoucher;
import org.prgms.voucher.voucher.FixedAmountVoucher;

import java.util.UUID;

public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public AmountVoucher createVoucher(UUID id, int amount) {
        return new FixedAmountVoucher(id, amount);
    }
}
