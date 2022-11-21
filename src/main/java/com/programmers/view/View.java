package com.programmers.view;

import com.programmers.customer.Customer;
import com.programmers.message.Message;
import com.programmers.voucher.voucher.Voucher;

import java.util.List;

public interface View{
    String getUserCommand();

    void printMessage(String message);

    void printMessage(Message message);

    void printVoucher(Voucher voucher);

    void printCustomer(Customer customer);

    void printList(List list);
}
