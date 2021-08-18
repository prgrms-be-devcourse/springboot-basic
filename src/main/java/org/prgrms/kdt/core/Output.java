package org.prgrms.kdt.core;

import org.prgrms.kdt.entity.Voucher;

public interface Output {
    void printMessage(String message);
    void displayVoucher(Voucher v);
    void inputError(String input);
}
