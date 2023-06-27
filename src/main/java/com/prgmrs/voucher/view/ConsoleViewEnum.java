package com.prgmrs.voucher.view;

public enum ConsoleViewEnum {
    SHOW_THE_LIST("list"),
    EXIT_THE_LOOP("exit"),
    CREATE_THE_VOUCHER("create"),
    CREATE_FIXED_AMOUNT_VOUCHER("fixed"),
    CREATE_PERCENT_DISCOUNT_VOUCHER("percent"),
    NOTHING_MATCHED("nothing"),
    SHOW_BLACKLIST("blacklist");

    private final String value;
    ConsoleViewEnum(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }

    public static ConsoleViewEnum findByCommand(String value) {
        for (ConsoleViewEnum enumValue : ConsoleViewEnum.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        return NOTHING_MATCHED;
    }


}
