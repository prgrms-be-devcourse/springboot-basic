package com.waterfogsw.voucher.console;

public interface Output {
    void printCommandList();

    void printVoucherTypes();

    void printCreatedVoucher(String voucher);

    void printAllVoucher(String voucherList);

    void printExitMessage();

    void printErrorMessage(String message);
}
