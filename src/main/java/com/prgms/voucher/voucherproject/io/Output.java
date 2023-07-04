package com.prgms.voucher.voucherproject.io;

public interface Output {
    void printErrorMsg();

    void printNoVoucher();

    void printMsg(String msg, boolean lnCheck);

}