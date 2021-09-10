package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;

public class ConsoleOutput implements Output {

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    @Override
    public void newLine() {
        System.out.println();
    }

    @Override
    public void printCustomers(Customer customer) {
        System.out.println(customer);
    }
}
