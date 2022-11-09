package com.programmers.voucher.io;

import com.programmers.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console<T> {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
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
        output.printOutput(Message.INTRO_MESSAGE);
    }

    public void requestVoucherType() {
        output.printOutput(Message.REQUEST_VOUCHER_TYPE_MESSAGE);
    }

    public void requestDiscountValue() {
        output.printOutput(Message.REQUEST_DISCOUNT_VALUE_MESSAGE);
    }

    public void printError() {
        output.printOutput(Message.WRONG_ORDER_MESSAGE);
    }

    public void printVouchers(List<Voucher> vouchers) {
        if(vouchers.isEmpty() || vouchers.size() == 0) {
            output.printOutput(Message.EMPTY_VOUCHER_MESSAGE);
            return;
        }
        vouchers.stream().forEach(output::printOutput);
    }
}
