package org.prgrms.orderapp.io;

import org.prgrms.orderapp.Voucher;

import java.util.List;

public interface Output {
    void vouchers(List<Voucher> vouchers);

    void inputError(String input);

    void printMessage(String msg);
}