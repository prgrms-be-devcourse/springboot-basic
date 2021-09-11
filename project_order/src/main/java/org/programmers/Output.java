package org.programmers;

import org.programmers.customer.Customer;
import org.programmers.voucher.Voucher;

import java.io.IOException;
import java.util.List;

public interface Output {

    void printPrompt() throws IOException;

    void printSign() throws IOException;

    void printVoucherTypes() throws IOException;

    void printVoucherList(List<Voucher> voucherList) throws IOException;

    void printBlackList(List<Customer> blackList) throws IOException;

    void askAmount() throws IOException;

    void askPercentage() throws IOException;

}
