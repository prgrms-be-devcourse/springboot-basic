package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void printCommandNotices();

    void printSelectVoucherType();

    void printSelectVoucherDiscountAmount();

    void printVoucherCreateMessage();

    void printAllVouchers(List<Voucher> vouchers);

    void printCustomers(List<Customer> customers);

    void printVoucherAssignCustomerEmail();

    void printCustomerName();

    void printCustomerEmail();

    void printDeleteVoucherMessage();

    void printVoucherId();
}
