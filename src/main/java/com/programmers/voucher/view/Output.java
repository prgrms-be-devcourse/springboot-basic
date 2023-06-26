package com.programmers.voucher.view;

import com.programmers.voucher.domain.Voucher;

public interface Output {
    void displayCommands();

    void displayVoucherCommands();

    void displayVoucher(Voucher voucher);
}
