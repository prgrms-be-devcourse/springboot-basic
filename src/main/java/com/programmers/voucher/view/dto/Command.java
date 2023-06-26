package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;

import java.util.Arrays;

public enum Command {
    EXIT("exit", "exit the program."),
    CREATE("create", "create a new voucher."),
    LIST("list", "list all vouchers.");

    private final String name;
    private final String text;

    Command(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public static Command findByName(String name) {
        return Arrays.stream(Command.values())
                .filter(command -> command.equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(ErrorMessage.INVALID_COMMAND));
    }

    private boolean equals(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
