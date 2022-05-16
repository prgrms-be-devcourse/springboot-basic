package com.programmers.springbootbasic.io;

import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;

import java.util.List;

public interface StandardOutput {

    void write(String message);

    void writeln(String message);

    void printNewCustomer(Customer customer);

    void printAllocationAcknowledgement(CustomerVoucherLog statusDTO);

    void printVouchersOfCustomer(String customerId, List<Voucher> vouchers);

    void printFoundCustomer(Customer customer);

    void printCustomerByVoucherId(Customer customer);

    void printDeletedCustomer(Customer customer);

    void printAllCustomers(List<Customer> customers);

    void printNewVoucher(Voucher voucherDTO);

    void printFoundVoucher(Voucher voucherDTO);

    void printAvailableVouchers(List<Voucher> vouchers);

    void printAllVouchers(List<Voucher> vouchers);

    void printAllVouchersFormatted(List<Voucher> vouchers);

    void printDeletedVoucher(Voucher voucherDTO);

}
