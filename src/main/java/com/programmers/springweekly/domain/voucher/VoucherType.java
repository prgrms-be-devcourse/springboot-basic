package com.programmers.springweekly.domain.voucher;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType findVoucherMenu(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Input: " + type + ", The type you are looking for is not found.");
        }
    }
}