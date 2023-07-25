package com.wonu606.vouchermanager.service.voucher.param;

public class VoucherCreateParam {

    private final String type;
    private final Double discountValue;

    public VoucherCreateParam(String type, Double discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public String getType() {
        return type;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
