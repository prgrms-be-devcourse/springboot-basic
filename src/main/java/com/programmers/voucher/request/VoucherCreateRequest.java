package com.programmers.voucher.request;

public class VoucherCreateRequest {
    private String type;
    private int amount;

    public VoucherCreateRequest(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
