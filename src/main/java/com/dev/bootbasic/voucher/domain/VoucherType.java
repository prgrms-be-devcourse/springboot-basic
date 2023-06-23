package com.dev.bootbasic.voucher.domain;

import java.util.Arrays;

public enum VoucherType {

    FIXED,
    PERCENT;

    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "생성할 수 없는 바우처 타입입니다.";

    public static VoucherType from(String name) {
        return Arrays.stream(values()).filter(voucherType -> voucherType.name()
                        .equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE));
    }

}
