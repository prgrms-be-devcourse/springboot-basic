package com.example.kdtspringmission.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID id;
    private final long amount;

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID id) {
        this(id, 100L);
    }

    @Override
    public long discountPrice(long price) {
        return price - amount;
    }

    public UUID getId() {
        return id;
    }
}
