package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class VoucherCreateRequest {

    private  String type;
    private double discountValue;

    public VoucherCreateRequest() {
    }

    public VoucherCreateRequest(String type, double discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
}
