package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum VoucherType {

    FIXED("1"),
    PERCENT("2");

    private final String typeNumber;

    VoucherType(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    public static VoucherType of(String voucherTypeNumber) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.typeNumber.equals(voucherTypeNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Voucher 타입입니다. => " + voucherTypeNumber));
    }
}
