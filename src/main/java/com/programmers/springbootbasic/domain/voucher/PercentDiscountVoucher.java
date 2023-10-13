package com.programmers.springbootbasic.domain.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {
    private final UUID id;
    private final long amount;

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
