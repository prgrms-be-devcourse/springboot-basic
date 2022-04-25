package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public enum VoucherType {
    FIXED, PERCENT;

    private static final String ERROR_MESSAGE_NOT_FOUND = "바우처 타입을 찾을 수 없습니다.";

    public static VoucherType from(String userInput) {
        try {
            return valueOf(userInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_FOUND);
        }
    }
}
