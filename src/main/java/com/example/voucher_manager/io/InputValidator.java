package com.example.voucher_manager.io;

public class InputValidator {
    public CommandType validate(String command) {
        if (CommandType.isValidated(command)){
            return CommandType.of(command);
        }
        return CommandType.ERROR;
    }
}
