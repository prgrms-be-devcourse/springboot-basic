package com.example.voucher.domain;

public class Voucher {
    private String voucherId;
    private double amount;

    public Voucher(String voucherId, double amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public double getAmount() {
        return amount;
    }
}

