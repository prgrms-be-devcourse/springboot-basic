package org.prgms.voucher.voucher.factory;

import org.prgms.voucher.voucher.AmountVoucher;

public interface AmountVoucherFactory {
    AmountVoucher createVoucher(int initialMoney, int amount);
}
