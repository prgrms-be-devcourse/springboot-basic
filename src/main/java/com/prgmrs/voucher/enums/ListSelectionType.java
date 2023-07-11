package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum ListSelectionType {
    SHOW_ALL_VOUCHERS("all"),
    SHOW_USER_WALLET("user"),
    SHOW_BLACKLIST("ban"),
    SHOW_VOUCHER_OWNER("owner"),
    BACK("back");

    private final String value;

    ListSelectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ListSelectionType of(String value) throws NoSuchChoiceException {
        for (ListSelectionType enumValue : ListSelectionType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such list type");
    }
}
