package com.programmers.springvoucherservice.view;

import com.programmers.springvoucherservice.domain.voucher.Voucher;

public interface View {
    void showMenu();

    String getUserCommand();

    void printMessage(String message);

    void printVoucher(Voucher voucher);
}
