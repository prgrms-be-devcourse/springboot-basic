package com.prgrms.springbasic.io;

import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;

import java.util.List;

public interface Output {
    void printConsoleMessage(ConsoleMessage consoleMessage);
    void printVouchers(List<VoucherResponse> vouchers);
    void printCustomers(List<CustomerResponse> customers);
}
