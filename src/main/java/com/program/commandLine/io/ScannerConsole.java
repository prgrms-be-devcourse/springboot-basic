package com.program.commandLine.io;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ScannerConsole implements Console {

    private final Input input;
    private final Output output;


    public ScannerConsole(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String input() {
        return input.input();
    }

    @Override
    public String input(String message) {
        return input.input(message);
    }

    @Override
    public boolean recheckInput(String message) {
        String answer = input.input(message);
        return Recheck.get(answer);
    }


    @Override
    public void menuView(MenuType menuType) {
        output.menuView(menuType);
    }

    @Override
    public void errorMessageView(String message) {
        output.messageView("Fail : " + message);
    }

    @Override
    public void successMessageView(String message) {
        output.messageView("Success : " + message);
    }

    @Override
    public void voucherListView(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            output.messageView("empty");
            return;
        }
        output.allVoucherView(vouchers);
    }

    @Override
    public void customerBlackListView(List<Customer> blackList) {
        if (blackList.isEmpty()) {
            output.messageView("empty");
            return;
        }
        output.customerBlackListView(blackList);
    }

    @Override
    public void customerView(Customer customer) {
        output.customerView(customer);
    }


}
