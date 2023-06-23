package com.programmers.voucher.dto;

public class VoucherRequestDto {
    private String voucherType;
    private long discountAmount;

    public VoucherRequestDto(String voucherType, long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

}

