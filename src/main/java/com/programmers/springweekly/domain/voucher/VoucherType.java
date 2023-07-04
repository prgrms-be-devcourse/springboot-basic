package com.programmers.springweekly.domain.voucher;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType findVoucherMenu(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 바우처 타입이 없습니다.");
        }
    }
}
