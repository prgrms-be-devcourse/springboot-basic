package me.programmers.springboot.basic.springbootbasic.voucher.dto;

public class VoucherCreateRequestDto {

    private String type;

    private long discountPrice;

    private long discountPercent;

    public VoucherCreateRequestDto(String type, long discountPrice, long discountPercent) {
        this.type = type;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
        return "VoucherCreateRequestDto{" +
                "type='" + type + '\'' +
                ", discountPrice=" + discountPrice +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
