package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum WalletAssignmentSelectionType {
    ASSIGN_VOUCHER("assign"),
    REMOVE_VOUCHER("remove"),
    BACK("back");

    private final String value;

    WalletAssignmentSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WalletAssignmentSelectionType of(String value) throws NoSuchChoiceException {
        for (WalletAssignmentSelectionType enumValue : WalletAssignmentSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such creation type");
    }
}
