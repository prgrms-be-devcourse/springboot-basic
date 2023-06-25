package com.prgmrs.voucher.service;

public enum ConsoleServiceEnum {
    SHOW_THE_LIST("list"),
    EXIT_THE_LOOP("exit"),
    CREATE_THE_VOUCHER("create"),
    CREATE_FIXED_AMOUNT_VOUCHER("fixed"),
    CREATE_PERCENT_DISCOUNT_VOUCHER("percent");

    private final String value;
    ConsoleServiceEnum(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }

    public static ConsoleServiceEnum findByCommand(String value) {
        for (ConsoleServiceEnum enumValue : ConsoleServiceEnum.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }


}
