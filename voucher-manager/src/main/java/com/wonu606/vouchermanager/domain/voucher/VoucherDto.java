package com.wonu606.vouchermanager.domain.voucher;

public class VoucherDto {

    private final String type;
    private final double discountValue;

    public VoucherDto(String type, double discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public String getType() {
        return type;
    }

    public double getDiscountValue() {
        return discountValue;
    }
}
