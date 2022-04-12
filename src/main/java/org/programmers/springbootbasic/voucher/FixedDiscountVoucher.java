package org.programmers.springbootbasic.voucher;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FixedDiscountVoucher implements Voucher {

    private final UUID id;
    private final long amount;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getAmount() {
        return amount;
    }

}
