package com.wonu606.vouchermanager.controller.voucher.reqeust;

public class VoucherCreateRequest {

    private final String type;
    private final String discountValue;

    public VoucherCreateRequest(String type, String discountValue) {
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
