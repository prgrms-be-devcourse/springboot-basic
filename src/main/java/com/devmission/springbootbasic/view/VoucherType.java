package com.devmission.springbootbasic.view;

import java.util.Arrays;

public enum VoucherType {

    FIXED,
    PERCENT;

    public static VoucherType from(String name) {
        return Arrays.stream(values()).filter(voucherType -> voucherType.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택할 수 없는 타입입니다."));
    }

}
