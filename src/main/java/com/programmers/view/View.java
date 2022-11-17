package com.programmers.view;

import com.programmers.customer.Customer;
import com.programmers.message.Message;
import com.programmers.voucher.voucher.Voucher;

public interface View {
    String getUserCommand();

    void printMessage(String message);

    void printMessage(Message message);

    void printVoucher(Voucher voucher);

    void printCustomer(Customer customer);
}
