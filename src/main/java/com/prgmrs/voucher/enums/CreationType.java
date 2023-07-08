package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum CreationType {
    CREATE_VOUCHER("voucher"),
    CREATE_USER("user");
    private final String value;

    CreationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CreationType of(String value) throws NoSuchChoiceException {
        for (CreationType enumValue : CreationType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such creation type");
    }
}
