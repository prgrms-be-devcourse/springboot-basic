package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;

public interface Output {
    void printMenu();

    void printMessage(String errorMessage);

    void printVoucherList(List<Voucher> vouchers);

    void printBlackList(List<Customer> blackList);
}
