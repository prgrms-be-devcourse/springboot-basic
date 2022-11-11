package com.prgrms.springbootbasic.voucher.domain;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;

import java.util.UUID;

public class PercentVoucher implements Voucher {

    private static final int MAX_AMOUNT_BOUNDARY = 99;
    private static final int MIN_AMOUNT_BOUNDARY = 1;

    UUID id;
    String voucherType;
    int percent;

    public PercentVoucher(String voucherType, int percent) {
        validate(voucherType, percent);
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.percent = percent;
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
        return percent;
    }
}
