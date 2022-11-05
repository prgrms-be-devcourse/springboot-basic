package org.programmers.spbw1.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public class RatioDiscountVoucher implements Voucher{
    private final UUID Id;
    private final long percent;

    public RatioDiscountVoucher(UUID id, long percent) {
        Id = id;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherID() {
        return this.Id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
