package org.prgms.voucherProgram.console.view;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.voucher.domain.Voucher;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printCustomer(Customer customer);

    void printVouchers(List<Voucher> vouchers);

    void printCustomers(List<Customer> customers);

    void printSuccess();

    void printError(String message);
}
