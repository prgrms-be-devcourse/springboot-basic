package com.example.kdtspringmission.voucher.domain;

import java.util.UUID;

public class RateAmountVoucher implements Voucher {

    private final UUID id;
    private final long percent;

    public RateAmountVoucher(UUID id, long percent) {
        this.id = id;
        this.percent = percent;
    }

    public RateAmountVoucher(UUID id) {
        this(id, 10L);
    }

    @Override
    public long discountPrice(long price) {
        return (long) (price - (price * ((double)percent / 100)));
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
