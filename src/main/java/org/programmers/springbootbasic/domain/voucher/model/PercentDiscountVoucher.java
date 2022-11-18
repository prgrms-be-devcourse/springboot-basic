package org.programmers.springbootbasic.domain.voucher.model;

import org.programmers.springbootbasic.exception.WrongRangeInputException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT_AMOUNT = 100;
    private static final int MIN_PERCENT_AMOUNT = 0;
    private final UUID voucherId;
    private final long amount;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        validateAmountRange();
    }

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
        return (long) (originalPrice - ((double) amount / (double) 100));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", " +
                voucherId.toString() + ", " +
                amount;
    }

    private void validateAmountRange() {
        if (!(MIN_PERCENT_AMOUNT <= amount && amount <= MAX_PERCENT_AMOUNT))
            throw new WrongRangeInputException("올바르지 않은 Amount의 값 범위입니다.");
    }
}
