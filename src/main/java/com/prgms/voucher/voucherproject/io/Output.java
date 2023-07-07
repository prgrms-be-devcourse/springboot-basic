package com.prgms.voucher.voucherproject.io;

import com.prgms.voucher.voucherproject.domain.Voucher;

public interface Output {
    void printErrorMsg();

    void printNoVoucher();

    void printMessage(String msg, boolean lnCheck);

    void printVoucherInfo(Voucher voucher);

}