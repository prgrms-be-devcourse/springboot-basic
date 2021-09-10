package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;

public interface Output {

    void printVoucher(Voucher voucher);

    void newLine();

    void printCustomers(Customer customer);
}
