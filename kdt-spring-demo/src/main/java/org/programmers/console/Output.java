package org.programmers.console;

import org.programmers.customer.model.Customer;
import org.programmers.voucher.model.Voucher;

import java.util.List;

public interface Output {
    void initInfoOutput();

    void createTypeInfoOutput();

    void showInputError();

    void listInfoOutput();

    void exitOutput();

    void createNumberInfoOutput();

    void blackListInfoOutput();

    void printBlackList(List<Customer> customerList);

    void printVoucherList(List<Voucher> voucherList);

}
