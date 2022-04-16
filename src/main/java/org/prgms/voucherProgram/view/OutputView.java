package org.prgms.voucherProgram.view;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.CustomerDto;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printCustomer(CustomerDto customerDto);

    void printVouchers(List<Voucher> vouchers);

    void printCustomers(List<Customer> customers);

    void printError(String message);
}
