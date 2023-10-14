package org.prgrms.vouchermanager.domain;

import org.prgrms.vouchermanager.exception.InputValueException;

public enum VoucherType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    FIXED("fixed"),
    PERCENT("percent");
    private final String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherType fromValue(String value) {
        for (VoucherType menu : VoucherType.values()) {
            if (menu.value.equalsIgnoreCase(value)) {
                return menu;
            }
        }
        throw new InputValueException();
    }
}
