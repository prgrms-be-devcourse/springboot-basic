package com.example.weeklymission3.models;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED("Fixed Amount Voucher"),
    PERCENT("Percent Discount Voucher");

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }

    public static VoucherType checkVoucherType(String type) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.toString().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력값"));
    }

    public static String transferVoucherType(VoucherType type) {
        return type.voucher;
    }
}
