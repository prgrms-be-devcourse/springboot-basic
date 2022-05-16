package me.programmers.springboot.basic.springbootbasic.voucher.dto;

public class VoucherUpdateRequestDto {
    private long discountPrice;

    private long discountPercent;

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(long discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "VoucherUpdateRequestDto{" +
                "discountPrice='" + discountPrice + '\'' +
                ", discountPercent='" + discountPercent + '\'' +
                '}';
    }
}
