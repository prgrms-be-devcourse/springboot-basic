package org.prgms.voucherProgram.view;

import java.util.List;

import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.entity.voucher.Voucher;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printVouchers(List<Voucher> vouchers);

    void printCustomers(List<Customer> users);

    void printError(String message);
}
