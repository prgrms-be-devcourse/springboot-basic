package com.devmission.springbootbasic.view;

import com.devmission.springbootbasic.Command;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
public class ConsoleManager {

    private static final String INPUT_COMMAND_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n";
    private static final String INPUT_VOUCHER_MESSAGE = "=== Voucher Type Choice ===\n" +
            "Type fixed {Amount}\n" +
            "Type percent {1 ~ 100}\n";

    private final View view;

    public ConsoleManager(View view) {
        this.view = view;
    }

    public Command readCommand() throws IOException {
        return showAndRead(INPUT_COMMAND_MESSAGE, Command::from);
    }

    public VoucherRequest readVoucherType() throws IOException {
        String inputLine = showAndRead(INPUT_VOUCHER_MESSAGE, String::trim);
        String[] voucher = inputLine.split(" ");

        VoucherType type = VoucherType.from(voucher[0]);
        int amount = Integer.parseInt(voucher[1]);
        return new VoucherRequest(type, amount);
    }

    private <T> T showAndRead(String message, Function<String, T> converter) throws IOException {
        view.printMessage(message);
        return converter.apply(view.inputReadLine());
    }

}
