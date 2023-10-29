package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface OutputHandler {

    void outputString(String message);

    void outputCustomer(Customer customer);

    void outputVoucher(Voucher voucher);

    void outputVouchers(List<Voucher> voucherList);

    void outputBlackList(List<Customer> customerList);
}
