package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.List;

public interface OutputConsole {
    void printVoucherList(List<Voucher> list);

    void printCustomerList(List<Customer> list);

    void printCommandErrorMessage();

    void printSqlErrorMessage();

    void printFailedAllocation();


}
