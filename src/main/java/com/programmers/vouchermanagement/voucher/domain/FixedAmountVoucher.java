package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    public static final int MIN_AMOUNT = 1;

    private final UUID id;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        validationAmount(amount);
        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    private void validationAmount(int amount) {
        if (amount <= MIN_AMOUNT) {
            throw new IllegalArgumentException("The minimum discount amount is 1.");
        }
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public int discount(int originalPrice) {
        if (amount > originalPrice) {
            return 0;
        }
        return originalPrice - amount;
    }
}
