package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.util.List;

public interface ConsoleOutput {
    void printCommandSet();

    void printCustomerCommandSet();

    void printVoucherCommandSet();

    void printVouchers(List<Voucher> vouchers);

    void printCustomers(List<Customer> customers);

    void print(String result);

    void exit();
}
