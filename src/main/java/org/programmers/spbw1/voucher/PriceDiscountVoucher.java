package org.programmers.spbw1.voucher;

import java.util.UUID;

public class PriceDiscountVoucher implements Voucher{
    private final UUID Id;
    private final long amount;

    public PriceDiscountVoucher(UUID id, long amount) {
        Id = id;
        this.amount = amount;
    }


    @Override
    public UUID getVoucherID() {
        return this.Id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
