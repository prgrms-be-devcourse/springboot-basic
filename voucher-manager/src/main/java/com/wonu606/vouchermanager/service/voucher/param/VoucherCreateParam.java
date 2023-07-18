package com.wonu606.vouchermanager.service.voucher.param;

public class VoucherCreateParam {

    private final String type;
    private final double discountValue;

    public VoucherCreateParam(String type, double discountValue) {
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
