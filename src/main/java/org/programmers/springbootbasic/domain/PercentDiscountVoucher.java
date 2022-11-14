package org.programmers.springbootbasic.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long originalPrice) {
        // 실세계에서 할인 시 소수 단위는 제거하므로 long type으로 정의
        return (long)(originalPrice - ((double)amount / (double)100));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", " +
                voucherId.toString() + ", " +
                amount;
    }
}
