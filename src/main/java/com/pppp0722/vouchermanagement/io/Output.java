package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;

import java.util.List;

public interface Output {
    void printLogo();
    void printMenu();
    void printEmpty();
    void printInputError();
    void printVoucherTypeInputRequest();
    void printAmountInputRequest();
    void printVoucherEmpty();
    void printVoucherList(List<Voucher> voucherList);
}
