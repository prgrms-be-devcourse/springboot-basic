package com.programmers.voucher.dto;

public record VoucherRequestDto(String voucherType, long discountAmount) {

    public VoucherRequestDto {
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

}

