package me.programmers.springboot.basic.springbootbasic.voucher.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class VoucherCreateRequestDto {

    @NotBlank
    private String type;

    @PositiveOrZero
    private long discountPrice;

    @Min(value = 0)
    @Max(value = 99)
    private long discountPercent;

    public VoucherCreateRequestDto(String type, long discountPrice, long discountPercent) {
        this.type = type;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
    }

    public String getType() {
        return type;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public long getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public String toString() {
        return "VoucherCreateRequestDto{" +
                "type='" + type + '\'' +
                ", discountPrice=" + discountPrice +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
