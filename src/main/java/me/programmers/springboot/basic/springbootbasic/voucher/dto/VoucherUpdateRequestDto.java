package me.programmers.springboot.basic.springbootbasic.voucher.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

public class VoucherUpdateRequestDto {

    @PositiveOrZero
    private long discountPrice;

    @Min(value = 0)
    @Max(value = 99)
    private long discountPercent;

    public VoucherUpdateRequestDto(long discountPrice, long discountPercent) {
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public long getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public String toString() {
        return "VoucherUpdateRequestDto{" +
                "discountPrice='" + discountPrice + '\'' +
                ", discountPercent='" + discountPercent + '\'' +
                '}';
    }
}
