package com.devcourse.voucher.domain;

public enum VoucherType {
    FIXED,
    PERCENT,
    ;

    private static final String NOT_SUPPORT_TYPE = "[Error] Your Input Is Not Support. Type : ";

    public static VoucherType from(String input) {
        try {
            return Enum.valueOf(VoucherType.class, input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_SUPPORT_TYPE + input);
        }
    }

    public boolean isPercent() {
        return this == PERCENT;
    }
}
