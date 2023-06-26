package com.programmers.console.util;

import com.programmers.global.exception.VoucherCommandException;

import java.util.Arrays;

public enum Command {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String type;

    Command(String type) {
        this.type = type;
    }

    public static Command of(String type) {
        return Arrays.stream(Command.values())
                .filter(menuType -> menuType.type.equals(type))
                .findFirst()
                .orElseThrow(VoucherCommandException::new);
    }
}
