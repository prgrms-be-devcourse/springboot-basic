package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum AssignmentSelectionType {
    ASSIGN_VOUCHER("assign"),
    FREE_VOUCHER("free"),
    BACK("back");

    private final String value;

    AssignmentSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AssignmentSelectionType of(String value) throws NoSuchChoiceException {
        for (AssignmentSelectionType enumValue : AssignmentSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such creation type");
    }
}
