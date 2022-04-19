package com.mountain.voucherApp.io;

import com.mountain.voucherApp.voucher.VoucherEntity;

import java.util.List;

public interface Output {
    void printManual();
    void printWrongInput();
    void choiceDiscountPolicy();
    void printAmount();
    void printAllList(List<VoucherEntity> repository);
    void printException(Exception e);
    void close();
}
