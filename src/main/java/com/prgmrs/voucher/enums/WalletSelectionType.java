package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum WalletSelectionType {
    ASSIGN_VOUCHER("assign"),
    FREE_VOUCHER("free"),
    BACK("back");

    private final String value;

    WalletSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WalletSelectionType of(String value) throws NoSuchChoiceException {
        for (WalletSelectionType enumValue : WalletSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such creation type");
    }
}
