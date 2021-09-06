package org.prgrms.kdtspringorder.io.abstraction;

import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void printVoucherList(List<Voucher> listToPrint);

    void printVoucher(Voucher voucher);

    void printCustomerList(List<Customer> customers);

    void print(String string);
}
