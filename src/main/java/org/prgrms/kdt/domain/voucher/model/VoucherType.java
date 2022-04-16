package org.prgrms.kdt.domain.voucher.model;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("FIXED"),
    PERCENT_DISCOUNT("PERCENT");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType findVoucherType(String type){
        return Arrays.stream(VoucherType.values())
                .filter(value -> value.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 바우처타입 입니다."));
    }

    public static String getValue(VoucherType voucherType){
        return voucherType.type;
    }
}
