package com.prgmrs.voucher.enums;

public enum ConsoleViewOptionEnum {
    SHOW_THE_LIST("list"),
    EXIT_THE_LOOP("exit"),
    CREATE_THE_VOUCHER("create"),
    SHOW_BLACKLIST("blacklist"),
    UNEXPECTED_INPUT;


    private final String value;

    ConsoleViewOptionEnum(String value) {
        this.value = value;
    }

    ConsoleViewOptionEnum() {
        this.value = "";
    }

    private String getValue() {
        return value;
    }

    public static ConsoleViewOptionEnum findByCommand(String value) {
        for (ConsoleViewOptionEnum enumValue : ConsoleViewOptionEnum.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        return UNEXPECTED_INPUT;
    }
}
