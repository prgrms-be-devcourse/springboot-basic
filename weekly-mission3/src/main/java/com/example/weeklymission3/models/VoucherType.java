package com.example.weeklymission3.models;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucher;

    VoucherType(String voucher) {
        this.voucher = voucher;
    }
}
