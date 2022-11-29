package com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request;

public class CreateVoucherRequest {
    private final String voucherType;
    private final long discount;

    public CreateVoucherRequest(String voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}

