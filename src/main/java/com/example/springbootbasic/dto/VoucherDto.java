package com.example.springbootbasic.dto;

import com.example.springbootbasic.domain.voucher.Voucher;

public class VoucherDto {

    private Long voucherId;
    private Long discountValue;
    private String voucherType;

    public VoucherDto() {
    }

    public VoucherDto(Long discountValue, String voucherType) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public VoucherDto(Long voucherId, Long discountValue, String voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType().getVoucherType());
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
