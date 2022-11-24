package com.example.weeklymission3.models;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final String type;
    private final int discount;

    public Voucher(UUID voucherId, String type, int discount) {
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public int getDiscount() {
        return discount;
    }
}
