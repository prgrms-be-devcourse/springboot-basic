package com.example.voucher;

public class CommandHandler {
    public void handleCommand(String command) {
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
