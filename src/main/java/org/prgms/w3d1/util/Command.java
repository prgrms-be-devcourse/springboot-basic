package org.prgms.w3d1.util;

import java.util.Arrays;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    VOUCHER_SERVICE("1"),
    BLACKLIST_SERVICE("2"),
    FIXED_AMOUNT_VOUCHER("3"),
    PERCENT_DISCOUNT_VOUCHER("4");

    private final String command;

    Command(String command){
        this.command = command;
    }

    public static Command getCommand(String str) {
        return Arrays.stream(values())
                .filter(v -> v.command.equals(str))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + str));
    }
}

