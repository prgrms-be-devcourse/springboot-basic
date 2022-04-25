package org.prgrms.kdt.io;

import org.prgrms.kdt.model.voucher.VoucherType;

public interface Output {
    void printMessage(String message);
    void printCommandManual(String manuals);
    void printVoucherValue(VoucherType voucherType);
    void printWarnMessage(Exception e);
    void printErrorMessage(Exception e);
}
