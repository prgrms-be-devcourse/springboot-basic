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
        String[] parts = command.split(" ");
        String action = parts[0];

        if (action.equalsIgnoreCase("create")) {
            handleCreateCommand(parts);
        }
        if (action.equalsIgnoreCase("list")) {
            handleListCommand();
        }
    }

    private void handleListCommand() {
        // List 커맨드 구현
    }

    private void handleCreateCommand(String[] parts) {
        // Handle 커맨드 구현
    }
}
