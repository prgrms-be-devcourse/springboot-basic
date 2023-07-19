package com.prgmrs.voucher.controller.mvc.dto;

public class VoucherMvcCreateRequest {
    private String discountType;
    private String discountStringValue;

    public VoucherMvcCreateRequest() {
        // Do nothing for thymeleaf support purpose
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountStringValue() {
        return discountStringValue;
    }

    public void setDiscountStringValue(String discountStringValue) {
        this.discountStringValue = discountStringValue;
    }
}