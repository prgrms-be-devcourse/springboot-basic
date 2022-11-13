package com.prgrms.springbootbasic.voucher.domain;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_AMOUNT_BOUNDARY = 10000;
    private static final int MIN_AMOUNT_BOUNDARY = 1;

    private final UUID id;
    private final String voucherType;
    private final int fixedAmount;

    public FixedAmountVoucher(String voucherType, int fixedAmount) {
        validate(voucherType, fixedAmount);
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.fixedAmount = fixedAmount;
    }

    public FixedAmountVoucher(UUID uuid, String voucherType, int fixedAmount) {
        validate(voucherType, fixedAmount);
        this.id = uuid;
        this.voucherType = voucherType;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public void validate(String voucherType, int discountAmount) {
        if (discountAmount < MIN_AMOUNT_BOUNDARY || discountAmount > MAX_AMOUNT_BOUNDARY) {
            throw new AmountOutOfBoundException(voucherType, MIN_AMOUNT_BOUNDARY, MAX_AMOUNT_BOUNDARY);
        }
    }

    @Override
    public String getVoucherType() {
        return voucherType;
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
