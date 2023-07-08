package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum ListSelectionType {
    SHOW_ENTIRE_LIST("all"),
    SHOW_USER_LIST("user"),
    SHOW_BLACKLIST("ban"),
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
