package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.util.Messages;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInteractionAggregator{
    private final Console console;

    public ConsoleInteractionAggregator(Console console) {
        this.console = console;
    }

    public String collectMenuInput() {
        return console.collectStringInput(Messages.SELECT_MENU.getMessage());
    }

    public CreateVoucherRequest collectVoucherInput() {
        String title = console.collectStringInput(Messages.VOUCHER_REGISTER_TYPE.getMessage());
        int amount = console.collectIntegerInput(Messages.VOUCHER_REGISTER_AMOUNT.getMessage());
        return CreateVoucherRequest.from(title, amount);
    }

    public void displayMessage(String message) {
        console.print(message);
    }
}
