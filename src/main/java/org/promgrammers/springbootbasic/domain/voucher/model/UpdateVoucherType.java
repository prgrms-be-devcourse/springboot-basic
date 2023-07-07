package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum UpdateVoucherType {

    ASSIGN("1"),
    BY_ID("2"),
    REMOVE("3");

    private final String type;

    UpdateVoucherType(String type) {
        this.type = type;
    }

    public static UpdateVoucherType from(String inputType) {
        return Arrays.stream(values())
                .filter(updateVoucher -> updateVoucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}