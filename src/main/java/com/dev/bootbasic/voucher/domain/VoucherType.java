package com.dev.bootbasic.voucher.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherType {

    FIXED,
    PERCENT;

    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "생성할 수 없는 바우처 타입입니다.";
    private static final String VOUCHER_TYPE_NULL_MESSAGE = "바우처 타입이 입력되지 않았습니다.";

    public static VoucherType from(String name) {
        if (name == null) {
            throw new IllegalArgumentException(VOUCHER_TYPE_NULL_MESSAGE);
        }
        return Arrays.stream(values()).filter(voucherType -> voucherType.name()
                        .equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(INVALID_VOUCHER_TYPE_MESSAGE));
    }

}
