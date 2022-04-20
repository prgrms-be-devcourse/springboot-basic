package com.mountain.voucherApp.io;

import com.mountain.voucherApp.customer.Customer;
import com.mountain.voucherApp.voucher.VoucherEntity;

import java.util.List;

public interface Output {
    void printMessage(String msg);
    void printManual();
    void choiceDiscountPolicy();
    void printVoucherList(List<VoucherEntity> voucherEntityList);
    void printCustomerList(List<Customer> customerList);
    void printCustomerVoucherInfo(List<Customer> customerList);
    void printException(Exception e);
    void close();
}
