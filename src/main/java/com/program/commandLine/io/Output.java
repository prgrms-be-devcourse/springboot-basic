package com.program.commandLine.io;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.voucher.Voucher;

import java.util.List;

public interface Output {
    void menuView();

    void messageView(String message);


    void allVoucherView(List<Voucher> vouchers);

    void customerBlackListView(List<Customer> blackList);
}
