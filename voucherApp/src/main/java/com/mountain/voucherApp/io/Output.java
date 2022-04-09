package com.mountain.voucherApp.io;

import com.mountain.voucherApp.voucher.Voucher;

import java.util.List;

public interface Output {
    void printManual();
    void printWrongInput(String input);
    void choiceDiscountPolicy();
    void printAmount();
    void printAllList(List<Voucher> repository);
}
