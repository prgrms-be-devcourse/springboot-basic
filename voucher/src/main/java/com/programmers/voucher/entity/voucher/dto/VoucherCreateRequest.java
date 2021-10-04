package com.programmers.voucher.entity.voucher.dto;

public class VoucherCreateRequest {
    private final String name;
    private final String type;
    private final int amount;
    private final Long owner;

    public VoucherCreateRequest(String name, String type, int amount, Long owner) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Long getOwner() {
        return owner;
    }
}