package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum DeleteVoucherType {

    ID("1"),
    ALL("2");

    private final String type;

    DeleteVoucherType(String type) {
        this.type = type;
    }

    public static DeleteVoucherType from(String inputType) {
        return Arrays.stream(values())
                .filter(deleteVoucher -> deleteVoucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}
