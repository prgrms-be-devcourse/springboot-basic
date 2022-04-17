package com.mountain.voucherApp.io;

import com.mountain.voucherApp.customer.Customer;
import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherEntity;

import java.util.List;

public interface Output {
    void printManual();
    void printWrongInput();
    void choiceDiscountPolicy();
    void printAmount();
    void printVoucherList(List<VoucherEntity> repository);
    void printCustomerList(List<Customer> repository);
    void printException(Exception e);
}
