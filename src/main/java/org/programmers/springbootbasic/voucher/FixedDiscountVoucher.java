package org.programmers.springbootbasic.voucher;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FixedDiscountVoucher implements Voucher {

    private final UUID id;
    private final int amount;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

}
