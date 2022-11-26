package com.program.commandLine.io;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.voucher.Voucher;

import java.util.List;

public interface Output {
    void menuView(MenuType menuType);

    void messageView(String message);

    void allVoucherView(List<Voucher> vouchers);

    void customerBlackListView(List<Customer> blackList);

    void customerView(Customer customer);
}
