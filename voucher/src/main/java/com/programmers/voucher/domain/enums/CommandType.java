package com.programmers.voucher.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    BLACKLIST("black"),
    EXIT("exit"),
    CUSTOMER("customer"),
    VOUCHER("voucher")
    ;

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
