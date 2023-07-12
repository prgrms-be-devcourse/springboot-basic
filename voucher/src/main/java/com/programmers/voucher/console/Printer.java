package com.programmers.voucher.console;

import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.domain.voucher.Voucher;

import java.util.List;
import java.util.Map;

public interface Printer {
    void printVoucher(Voucher voucher);

    void printListOfVoucher(Map<String, Voucher> voucherList);

    void printListOfCustomer(List<Customer> customerList);

    void printCustomer(Customer customer);

    void printError(Exception e);

    void printBlackList(List<String> blackList);

    void printEndMessage();
}
