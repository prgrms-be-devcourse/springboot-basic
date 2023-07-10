package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.NoSuchChoiceException;

public enum ManagementType {
    CREATE_MODE("create"),
    LIST_MODE("list"),
    WALLET_MODE("wallet"),
    EXIT_THE_LOOP("exit");


    private final String value;

    ManagementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ManagementType of(String value) throws NoSuchChoiceException {
        for (ManagementType enumValue : ManagementType.values()) {
            if (enumValue.getValue().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new NoSuchChoiceException("no such option exists");
    }
}
