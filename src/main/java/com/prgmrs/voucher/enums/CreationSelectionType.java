package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum CreationSelectionType {
    CREATE_VOUCHER("voucher"),
    CREATE_USER("user"),
    BACK("back");

    private final String value;

    CreationSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CreationSelectionType of(String value) throws NoSuchChoiceException {
        for (CreationSelectionType enumValue : CreationSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such creation type");
    }
}
