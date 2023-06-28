package com.example.voucher.domain;

import com.example.voucher.domain.discount.DiscountPolicy;

public class Voucher {
    private final String voucherId;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(DiscountPolicy discountPolicy) {
        return discountPolicy.applyDiscount(this);
    }
}

