package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;

public class VoucherWebCreateRequestDto {
    private Integer discount;
    private VoucherDiscountType voucherDiscountType;

    public VoucherWebCreateRequestDto(Integer discount, VoucherDiscountType voucherDiscountType) {
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public Integer discount() {
        return discount;
    }

    public VoucherDiscountType voucherDiscountType() {
        return voucherDiscountType;
    }
}
