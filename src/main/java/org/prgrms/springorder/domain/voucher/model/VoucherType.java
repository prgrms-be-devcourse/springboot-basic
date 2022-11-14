package org.prgrms.springorder.domain.voucher.model;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String type;

    VoucherType(String voucherType) {
        this.type = voucherType;
    }

    public static VoucherType of(String voucherType) {
        return Arrays.stream(values())
            .filter(voucher -> Objects.equals(voucher.type, voucherType.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
    }

}
