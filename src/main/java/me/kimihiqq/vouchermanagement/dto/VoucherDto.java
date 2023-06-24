package me.kimihiqq.vouchermanagement.dto;

public class VoucherDto {
    private String type;
    private long discount;

    public VoucherDto(String type, long discount) {
        this.type = type;
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public long getDiscount() {
        return discount;
    }
}