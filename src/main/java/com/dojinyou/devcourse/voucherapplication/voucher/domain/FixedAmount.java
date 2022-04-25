package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public class FixedAmount extends VoucherAmount {
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_AMOUNT = 1_000_000_000;
    public static final String ERROR_MESSAGE_OUT_OT_RANGE = "voucher amount 범위를 벗어났습니다.";

    public FixedAmount(int amount) {
        super(amount);
        validate(amount);
    }

    @Override
    public void validate(int amount) {
        if (amount < MIN_AMOUNT || MAX_AMOUNT < amount) {
            throw new IllegalArgumentException(ERROR_MESSAGE_OUT_OT_RANGE);
        }
    }
}
