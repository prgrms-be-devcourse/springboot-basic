package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class VoucherCreateRequest {

    private final String type;
    private final double discountValue;

    public VoucherCreateRequest(String type, double discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public String getType() {
        return type;
    }

    public double getDiscountValue() {
        return discountValue;
    }
}
