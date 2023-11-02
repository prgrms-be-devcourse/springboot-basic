package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.util.EnumNamePattern;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VoucherWebCreateRequestDto {
    @NotNull @Min(10) @Max(10)
    private Integer discount;
    @EnumNamePattern(regexp = "FIXED_AMOUNT_DISCOUNT|PERCENT_DISCOUNT", enumClass = VoucherDiscountType.class)
    private VoucherDiscountType voucherDiscountType;

    private VoucherWebCreateRequestDto() {
    }

    public VoucherWebCreateRequestDto(Integer discount, VoucherDiscountType voucherDiscountType) {
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public Integer getDiscount() {
        return discount;
    }

    public VoucherDiscountType getVoucherDiscountType() {
        return voucherDiscountType;
    }

}
