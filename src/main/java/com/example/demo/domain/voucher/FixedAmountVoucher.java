package com.example.demo.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final double discountAmount;

    public FixedAmountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        double result = beforeAmount - discountAmount;
        if (result < 0) {
            return 0;
        }
        return result;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }
}
