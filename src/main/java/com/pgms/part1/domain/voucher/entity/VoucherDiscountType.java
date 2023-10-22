package com.pgms.part1.domain.voucher.entity;

public enum VoucherDiscountType {
    FIXED_AMOUNT_DISCOUNT("Fixed Amount Discount","Amount"),
    PERCENT_DISCOUNT("Percent Discount","Percent");

    private final String discountType;
    private final String calculateType;

    VoucherDiscountType(String discountType, String calculateType) {
        this.discountType = discountType;
        this.calculateType = calculateType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getCalculateType() {
        return calculateType;
    }
}
