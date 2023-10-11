package com.programmers.springbootbasic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    @Getter
    private final UUID voucherId;
    private final long value;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - value;
    }
}
