package org.prgrms.kdt.io;

import org.prgrms.kdt.model.voucher.VoucherType;

public interface Output {
    void printMessage(String message);
    void printCommandManual();
    void printInvalidCommand();
    void printShutDownSystem();
    void printVoucherManual();
    void printVoucherUpdateManual();
    void printVoucherUpdateValue();
    void printVoucherValue(VoucherType voucherType);
    void printVoucherCreateSuccess(String voucherInfo);
    void printVoucherUpdateSuccess(String voucherInfo);
}
