package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    public static final int MIN_PERCENT = 1;
    public static final int MAX_PERCENT = 100;

    private final UUID id;
    private final int amount;

    public PercentDiscountVoucher(int amount) {
        validationAmount(amount);
        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    private void validationAmount(int amount) {
        if (MIN_PERCENT > amount || amount > MAX_PERCENT) {
            throw new IllegalArgumentException("The discount percentage must be between 1 and 100%.");
        }
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public int discount(int originalPrice) {
        return originalPrice * (amount / MAX_PERCENT);
    }
}
