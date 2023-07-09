package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum VoucherType {

    FIXED("1", "fixed"),
    PERCENT("2", "percent");

    private final String typeNumber;
    private final String typeName;

    VoucherType(String typeNumber, String typeName) {
        this.typeNumber = typeNumber;
        this.typeName = typeName;
    }

    public static VoucherType from(String voucherType) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.typeNumber.equals(voucherType) || voucher.typeName.equals(voucherType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Voucher 타입입니다. => " + voucherType));
    }
}