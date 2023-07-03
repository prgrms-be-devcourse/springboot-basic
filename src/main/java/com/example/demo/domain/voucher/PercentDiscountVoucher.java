package com.example.demo.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final double discountPercent;

    public PercentDiscountVoucher(double discountPercent) {
        this.id = UUID.randomUUID();
        this.discountPercent = discountPercent;
    }

    @Override
    public double discount(double beforeAmount) {
        return beforeAmount * (discountPercent / 100.0);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public double getDiscountAmount() {
        return discountPercent;
    }
}
