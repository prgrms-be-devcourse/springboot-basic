package org.prgms.kdtspringvoucher.io;

import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;

public interface Output {
    void printCommandTypeInputPrompt();
    void printWalletCommandTypeInputPrompt();
    void printVoucherTypeInputPrompt();

    void outputAmountOrPercentPrompt(VoucherType voucherType);
}
