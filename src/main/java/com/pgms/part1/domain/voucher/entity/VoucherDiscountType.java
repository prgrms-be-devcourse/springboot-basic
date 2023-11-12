package com.pgms.part1.domain.voucher.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static VoucherDiscountType findByCode(String code) {
        return Stream.of(VoucherDiscountType.values())
                .filter(v -> v.toString().equals(code))
                .findFirst().orElse(null);
    }
}
