package com.programmers.voucher.view;

import com.programmers.voucher.controller.customer.dto.CustomerResponse;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;

public interface Output {
    void displayCommands();

    void displayVoucherCommands();

    void displayCustomerCommands();

    void displayVoucherType();

    void displayCreatedVoucher(VoucherResponse voucher);

    void displayVoucher(VoucherResponse voucher);

    void displayCustomer(CustomerResponse customer);
}
