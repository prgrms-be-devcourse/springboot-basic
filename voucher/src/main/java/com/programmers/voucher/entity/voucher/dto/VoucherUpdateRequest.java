package com.programmers.voucher.entity.voucher.dto;

public class VoucherUpdateRequest {
    private final Long id;
    private final String name;
    private final String type;
    private final int amount;
    private final Long owner;

    public VoucherUpdateRequest(Long id, String name, String type, int amount, Long owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() {
        return id;
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
