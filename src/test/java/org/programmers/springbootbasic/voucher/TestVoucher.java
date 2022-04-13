package org.programmers.springbootbasic.voucher;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TestVoucher implements Voucher {
    private final UUID id;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - 1000;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return 0;
    }
}
