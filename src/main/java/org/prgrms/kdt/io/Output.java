package org.prgrms.kdt.io;

import org.prgrms.kdt.model.voucher.VoucherType;

public interface Output {
    void printMessage(String message);
    void printCommandManual(String manuals);
    void printInvalidCommand();
    void printShutDownSystem();
    void printVoucherType();
    void printVoucherUpdateManual();
    void printVoucherUpdateValue();
    void printVoucherDeleteManual();
    void printVoucherValue(VoucherType voucherType);
    void printVoucherCreateSuccess(String voucherInfo);
    void printVoucherUpdateSuccess(String voucherInfo);
    void printVoucherDeleteSuccess();
}
