package com.prgms.management.command.io;

import com.prgms.management.customer.entity.Customer;
import com.prgms.management.voucher.entity.Voucher;

import java.util.List;

public interface Output<T> {
    void printListCustomer (List<Customer> list);
    void printListVoucher (List<Voucher> list);
    void printOneVoucher(T t);
    void printString(String str);
}
