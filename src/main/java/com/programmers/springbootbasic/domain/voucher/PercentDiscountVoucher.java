package com.programmers.springbootbasic.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
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
