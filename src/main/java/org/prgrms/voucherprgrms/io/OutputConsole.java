package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.List;

public interface OutputConsole {
    void voucherList(List<Voucher> list);

    void customerList(List<Customer> list);

    void commandErrorMessage();

    void sqlErrorMessage();

    void failedAllocation();


}
