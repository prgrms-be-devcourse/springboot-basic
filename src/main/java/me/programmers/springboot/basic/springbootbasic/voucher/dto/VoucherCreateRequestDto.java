package me.programmers.springboot.basic.springbootbasic.voucher.dto;

public class VoucherCreateRequestDto {

    private String type;

    private String discountPrice;

    private String discountPercent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
        return "VoucherCreateRequestDto{" +
                "type='" + type + '\'' +
                ", discountPrice=" + discountPrice +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
