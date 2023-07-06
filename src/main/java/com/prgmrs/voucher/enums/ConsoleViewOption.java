package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchOptionException;

public enum ConsoleViewOption {
    SHOW_THE_LIST("list"),
    EXIT_THE_LOOP("exit"),
    CREATE_THE_VOUCHER("create"),
    SHOW_BLACKLIST("blacklist");

    private final String value;

    ConsoleViewOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConsoleViewOption of(String value) throws NoSuchOptionException {
        for (ConsoleViewOption enumValue : ConsoleViewOption.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchOptionException("no such option exists");
    }
}
