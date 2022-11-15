package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.domain.Customer;
import org.programmers.springbootbasic.domain.Voucher;

import java.util.List;

public interface Output {
    void printMainMenu();

    void printVouchers(List<Voucher> vouchers);

    void printBlacklist(List<Customer> blacklist);
}
