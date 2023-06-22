package com.programmers.voucher.request;

public class VoucherCreationRequest {
    private String type;
    private long amount;

    public VoucherCreationRequest(String type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
