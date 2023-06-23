package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final int amount;

    public PercentDiscountVoucher(UUID id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int discount(int originalPrice) {
        return originalPrice * (amount / 100);
    }
}
