package com.example.voucher.domain;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final double amount;

    public Voucher(UUID voucherId, double amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Voucher ID: " + voucherId + "\nAmount: " + amount;
    }
}

