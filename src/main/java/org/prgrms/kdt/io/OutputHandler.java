package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface OutputHandler {

    void outputSystemMessage(String message);

    void outputVouchers(List<Voucher> voucherList);

    void outputBlackList(List<Customer> customerList);
}
