package com.programmers.voucher.domain;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    BLACK("black"),
    EXIT("exit");

    private final String string;

    CommandType(String string) {
        this.string = string;
    }

    public static Optional<CommandType> convertStringToCommandType(String stringType) {
        return Arrays.stream(CommandType.values())
                .filter(t -> t.string.equals(stringType.toLowerCase()))
                .findAny();
    }
}
