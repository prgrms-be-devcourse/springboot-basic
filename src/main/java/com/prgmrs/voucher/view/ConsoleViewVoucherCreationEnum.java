package com.prgmrs.voucher.view;

public enum ConsoleViewVoucherCreationEnum {
    CREATE_FIXED_AMOUNT_VOUCHER("fixed"),
    CREATE_PERCENT_DISCOUNT_VOUCHER("percent"),
    UNEXPECTED_INPUT;

    private final String value;
    ConsoleViewVoucherCreationEnum(String value) {
        this.value = value;
    }

    ConsoleViewVoucherCreationEnum() {
        this.value = "";
    }

    private String getValue() {
        return value;
    }

    public static ConsoleViewVoucherCreationEnum findByCommand(String value) {
        for (ConsoleViewVoucherCreationEnum enumValue : ConsoleViewVoucherCreationEnum.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        return UNEXPECTED_INPUT;
    }
}
