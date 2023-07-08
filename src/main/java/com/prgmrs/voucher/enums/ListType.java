package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum ListType {
    SHOW_ENTIRE_LIST("list"),
    SHOW_USER_LIST("user"),
    SHOW_BLACKLIST("ban");
    private final String value;

    ListType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ListType of(String value) throws NoSuchChoiceException {
        for (ListType enumValue : ListType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such list type");
    }
}
