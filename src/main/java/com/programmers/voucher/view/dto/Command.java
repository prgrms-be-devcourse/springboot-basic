package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;

import java.util.Arrays;

public enum Command {
    EXIT("exit", "exit the program."),
    CREATE("create", "create a new voucher."),
    LIST("list", "list all vouchers.");

    private final String code;
    private final String text;

    Command(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Command findByCode(String code) {
        return Arrays.stream(Command.values())
                .filter(command -> command.equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(ErrorMessage.INVALID_COMMAND));
    }

    private boolean equals(String code) {
        return this.code.equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
