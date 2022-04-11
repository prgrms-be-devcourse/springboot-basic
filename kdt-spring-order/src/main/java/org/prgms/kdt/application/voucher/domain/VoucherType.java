package org.prgms.kdt.application.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("fixedAmount"),
    PERCENT_DISCOUNT("percentDiscount");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType findVoucherType (String type) {
        return Arrays.stream(values())
                .filter(i -> i.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 바우처 타입 입니다."));
    }
}
