package me.programmers.springboot.basic.springbootbasic.voucher.dto;

public class VoucherUpdateRequestDto {
    private String discountPrice;

    private String discountPercent;

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
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
