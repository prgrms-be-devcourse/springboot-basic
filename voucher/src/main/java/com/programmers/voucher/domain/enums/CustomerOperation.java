package com.programmers.voucher.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CustomerOperation {
    CREATE("create"),
    UPDATE("update"),
    FINDBYID("findbyid"),
    FINDALL("findall"),
    DELETEALL("deleteall"),
    DELETEBYID("deletebyid");;


    private final String mode;

    CustomerOperation(String mode) {
        this.mode = mode;
    }

    public static Optional<CustomerOperation> convertStringToCustomerOperation(String stringType) {
        return Arrays.stream(CustomerOperation.values())
                .filter(v -> v.mode.equals(stringType.toLowerCase()))
                .findAny();
    }
}
