package com.programmers.voucher.dto;

public class VoucherRequestDto {
    private String voucherType;
    private Long discountAmount;

    public VoucherRequestDto(String voucherType, Long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }
}

