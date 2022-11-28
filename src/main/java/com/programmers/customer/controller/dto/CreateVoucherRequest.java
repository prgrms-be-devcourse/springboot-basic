package com.programmers.customer.controller.dto;

public class CreateVoucherRequest {
    String type;
    long discount;

    public CreateVoucherRequest(String type, long discount) {
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
