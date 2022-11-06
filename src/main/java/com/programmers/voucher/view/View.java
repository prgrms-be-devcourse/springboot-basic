package com.programmers.voucher.view;

import com.programmers.voucher.domain.voucher.Voucher;

public interface View {
    void showMenu();

    String getUserCommand();

    void printMessage(String message);

    void printVoucher(Voucher voucher);
}
