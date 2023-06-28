package com.example.voucher.domain;

import com.example.voucher.domain.discount.DiscountPolicy;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private double amount;

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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(DiscountPolicy discountPolicy) {
        return discountPolicy.applyDiscount(this);
    }
}

