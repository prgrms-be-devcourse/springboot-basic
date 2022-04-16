package org.prgrms.kdtspringdemo.io;

import org.prgrms.kdtspringdemo.domain.voucher.Voucher;

import java.util.List;

public interface Output {
    void printPrompt();
    void printVoucherOptionPrompt();
    void printVoucherDiscountAmountPrompt();
    void printVoucherList(List<Voucher> vouchers);
    void printError(String message);
}
