package com.programmers.vouchermanagement.voucher.dto;

public class VoucherRequestDto {

    private final String voucherType;
    private final Long discount;

    public VoucherRequestDto(String voucherType, Long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Long getDiscount() {
        return discount;
    }
}
