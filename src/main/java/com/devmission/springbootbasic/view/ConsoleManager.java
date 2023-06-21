package com.devmission.springbootbasic.view;

import com.devmission.springbootbasic.Command;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleManager {

    private static final String INPUT_COMMAND_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n";

    private final View view;

    public ConsoleManager(View view) {
        this.view = view;
    }

    public Command showCommandAndRead() throws IOException {
        view.printMessage(INPUT_COMMAND_MESSAGE);
        return Command.from(view.inputReadLine().toUpperCase());
    }

}
