package com.dev.bootbasic.view;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViewManager {

    private static final String INPUT_COMMAND_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n";
    private final InputView inputView;
    private final OutputView outputView;

    public ViewManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Command readCommand() throws IOException {
        outputView.showMessage(INPUT_COMMAND_MESSAGE);
        return Command.from(inputView.inputLine());
    }

}
