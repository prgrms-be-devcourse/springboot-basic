package org.prgrms.kdt.app.configuration.io;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface OutputHandler {

    void outputSystemMessage(String message);

    void outputVouchers(List<Voucher> voucherList);

    void outputBlackList(List<Customer> customerList);
}
