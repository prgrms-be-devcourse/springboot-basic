package com.programmers.voucher.view;

import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;

public interface Output {
    void displayCommands();

    void displayVoucherCommands();

    void displayCustomerCommands();

    void displayVoucher(VoucherResponse voucher);

    void displayCustomer(CustomerResponse customer);
}
