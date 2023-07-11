package com.programmers.voucher.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherOperation {
    CREATE("create"),
    UPDATE("update"),
    FINDBYID("findbyid"),
    FINDALL("findall"),
    DELETEALL("deleteall"),
    DELETEBYID("deletebyid");

    private final String mode;

    VoucherOperation(String mode) {
        this.mode = mode;
    }

    public static Optional<VoucherOperation> convertStringToVoucherOperation(String stringType) {
        return Arrays.stream(VoucherOperation.values())
                .filter(v -> v.mode.equals(stringType.toLowerCase()))
                .findAny();
    }
}
