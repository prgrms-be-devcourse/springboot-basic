package com.wonu606.vouchermanager.controller.voucher.response;

public class VoucherCreateResponse {

    private final String type;
    private final String discountValue;

    public VoucherCreateResponse(String type, String discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public String getType() {
        return type;
    }

    public String getDiscountValue() {
        return discountValue;
    }
}
