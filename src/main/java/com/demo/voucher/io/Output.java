package com.demo.voucher.io;

import com.demo.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void showMenu();

    void showVoucherType();

    void inputError(String errorResponse);

    void showAllVouchers(List<Voucher> voucherHistory);

    void showExitMessage();
}
