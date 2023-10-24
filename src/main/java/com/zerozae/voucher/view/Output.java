package com.zerozae.voucher.view;


public interface Output {
    void printCommand();
    void printInfo(String voucherInfo);
    void printSystemMessage(String message);
    void printVoucherCommand();
    void printCustomerCommand();
}