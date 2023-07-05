package com.example.voucher;

import com.example.voucher.ui.Input;
import com.example.voucher.ui.Output;

public class CommandHandler {
    private Input input;
    private Output output;

    public CommandHandler(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void handleCommand() {
        String command = input.readInput();
        CommandEnum commandEnum = CommandEnum.fromString(command);

        switch (commandEnum) {
            case CREATE:
                handleCreateCommand();
                break;
            case LIST:
                handleListCommand();
                break;
        }
    }

    private void handleListCommand() {
        // List 커맨드 구현
    }

    private void handleCreateCommand(String[] parts) {
        // Handle 커맨드 구현
    }
}
