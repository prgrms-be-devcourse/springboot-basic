package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface ConsoleOutput {
    void printCommandSet();

    void printCustomerCommandSet();

    void printVoucherCommandSet();

    void printVouchers(List<VoucherDto> vouchers);

    void printCustomers(List<CustomerDto> customers);

    void print(String result);

    void exit();
}
