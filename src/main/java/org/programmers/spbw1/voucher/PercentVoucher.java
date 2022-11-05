package org.programmers.spbw1.voucher;

import java.util.UUID;


public class PercentVoucher implements Voucher{
    private final UUID Id;
    private final long percent;

    public PercentVoucher(UUID id, long percent) {
        Id = id;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherID() {
        return this.Id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }
}
