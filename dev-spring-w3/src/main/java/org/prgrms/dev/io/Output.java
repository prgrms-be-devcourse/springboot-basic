package org.prgrms.dev.io;

import org.prgrms.dev.customer.domain.Customer;
import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void init();

    void selectVoucherType();

    void printInvalidNumber();

    void printInvalidCommandType();

    void printInvalidVoucherType();

    void printBlackList(List<Customer> blackList);

    void printVoucherList(List<Voucher> voucherList);
}
