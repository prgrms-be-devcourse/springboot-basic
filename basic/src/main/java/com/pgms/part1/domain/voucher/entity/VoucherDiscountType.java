package com.pgms.part1.domain.voucher.entity;

public enum VoucherDiscountType {
    FIXEDAMOUNTDISCOUNT("Fixed Amount Discount","Amount"),
    PERCENTDISCOUNT("Percent Discount","Percent");

    private final String discountType;
    private final String calculateType;

    VoucherDiscountType(String discountType, String calculateType) {
        this.discountType = discountType;
        this.calculateType = calculateType;
    }
}
