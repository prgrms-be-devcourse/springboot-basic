package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum FindVoucherType {

    FIND_BY_VOUCHER_ID("1"),
    FIND_BY_CUSTOMER_ID("2");

    private final String type;

    FindVoucherType(String type) {
        this.type = type;
    }

    public static FindVoucherType from(String inputType) {
        return Arrays.stream(values())
                .filter(findVoucher -> findVoucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}