package com.programmers.voucher.view;

import com.programmers.voucher.voucher.Voucher;

public interface View {
    String getUserCommand();

    void printMessage(String message);

    void printVoucher(Voucher voucher);
}
