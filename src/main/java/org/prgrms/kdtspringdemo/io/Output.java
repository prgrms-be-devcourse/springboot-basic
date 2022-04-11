package org.prgrms.kdtspringdemo.io;

import org.prgrms.kdtspringdemo.domain.voucher.Voucher;

import java.util.List;

public interface Output {
    void printPrompt();
    void printVoucherPrompt();
    void printVoucherOptionPrompt();
    void printVoucherList(List<Voucher> vouchers);
    void printError(String message);

}
