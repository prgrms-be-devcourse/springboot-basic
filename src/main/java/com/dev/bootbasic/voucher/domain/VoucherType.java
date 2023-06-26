package com.dev.bootbasic.voucher.domain;

public enum VoucherType {

    FIXED,
    PERCENT;

    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "생성할 수 없는 바우처 타입입니다.";

    public static VoucherType from(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
        }
    }

}
