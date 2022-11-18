package com.programmers.voucher.io;

import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class View {
    private final Input input;
    private final Output output;

    public View(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input.input();
    }

    public long getInputDiscountValue() {
        return Long.parseLong(input.input());
    }

    public void requestMenuType() {
        output.printOutput(Message.INTRO_MESSAGE.toString());
    }

    public void requestVoucherType() {
        output.printOutput(Message.REQUEST_VOUCHER_TYPE_MESSAGE.toString());
    }

    public void requestDiscountValue() {
        output.printOutput(Message.REQUEST_DISCOUNT_VALUE_MESSAGE.toString());
    }

    public void requestVoucherId() {
        output.printOutput(Message.REQUEST_VOUCHER_ID.toString());
    }

    public void requestCustomerName() {
        output.printOutput(Message.REQUEST_CUSTOMER_NAME.toString());
    }

    public void requestEmail() {
        output.printOutput(Message.REQUEST_CUSTOMER_EMAIL.toString());
    }

    public void printError(String message) {
        output.printOutput(message);
    }

    public void printVouchers(List<Voucher> vouchers) {
        vouchers.stream().forEach(voucher -> output.printOutput(voucher.toString()));
    }

    public void printVoucher(Voucher voucher) {
        output.printOutput(voucher.toString());
    }

    public void printEmptyVouchers() {
        output.printOutput(Message.EMPTY_VOUCHER_MESSAGE.toString());
    }

    public void printDeleteAll() {
        output.printOutput(Message.DELETE_ALL_VOUCHERS.toString());
    }

    public void printCustomer(Customer customer) {
        output.printOutput(customer.getCustomerName() + Message.WELCOME_CUSTOMER);
    }
}
