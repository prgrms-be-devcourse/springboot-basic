package me.kimihiqq.vouchermanagement.dto;

public class VoucherDto {
    private String type;
    private String discount;

    public VoucherDto(String type, String discount) {
        this.type = type;
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public String getDiscount() {
        return discount;
    }
}