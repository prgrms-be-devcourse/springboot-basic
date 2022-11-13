package com.prgrms.springbootbasic.voucher.domain;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_AMOUNT_BOUNDARY = 10000;
    private static final int MIN_AMOUNT_BOUNDARY = 1;

    private final UUID id;
    private final int fixedAmount;

    public FixedAmountVoucher(int fixedAmount) {
        validate(fixedAmount);
        this.id = UUID.randomUUID();
        this.fixedAmount = fixedAmount;
    }

    @Override
    public void validate(int discountAmount) {
        if (discountAmount < MIN_AMOUNT_BOUNDARY || discountAmount > MAX_AMOUNT_BOUNDARY) {
            throw new AmountOutOfBoundException(this.getClass().getSimpleName(), MIN_AMOUNT_BOUNDARY, MAX_AMOUNT_BOUNDARY);
        }
    }

    @Override
    public UUID getUUID() {
        return id;
    }

    @Override
    public int getDiscountRate() {
        return fixedAmount;
    }
}
