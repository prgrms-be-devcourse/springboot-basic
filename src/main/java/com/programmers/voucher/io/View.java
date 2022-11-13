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

    public void printError(String message) {
        output.printOutput(message);
    }

    public void printVouchers(List<Voucher> vouchers) {
        if(vouchers.isEmpty() || vouchers.size() == 0) {
            output.printOutput(Message.EMPTY_VOUCHER_MESSAGE.toString());
            return;
        }
        vouchers.stream().forEach(voucher -> output.printOutput(voucher.toString()));
    }

    public void printBlacks(List<Customer> blacks) {
        if(blacks.isEmpty() || blacks.size() == 0) {
            output.printOutput(Message.EMPTY_VOUCHER_MESSAGE.toString());
            return;
        }
        blacks.stream().forEach(black -> output.printOutput(black.toString()));
    }
}
