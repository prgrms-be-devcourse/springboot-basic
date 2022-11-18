package com.program.commandLine.io;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.voucher.Voucher;

import java.util.List;

public interface Console {

    String input();

    String input(String message);

    void menuView(MenuType menuType);

    void errorMessageView(String message);

    void successMessageView(String message);

    void voucherListView(List<Voucher> vouchers);

    void customerBlackListView(List<Customer> blackList);

    void customerView(Customer customer);
}
