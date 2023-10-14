package com.prgrms.springbasic.dto;

public class CreateVoucherRequest {
    private String discountType;
    private long discountValue;

    public CreateVoucherRequest(String discountType, long discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
