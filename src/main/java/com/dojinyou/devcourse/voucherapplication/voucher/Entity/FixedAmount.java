package com.dojinyou.devcourse.voucherapplication.voucher.Entity;

public class FixedAmount implements VoucherAmount {
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_AMOUNT = 1_000_000_000;
    public static final String ERROR_MESSAGE_OUT_OT_RANGE = "voucher amount 범위를 벗어났습니다.";
    private final int amount;

    public FixedAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    @Override
    public void validate(int amount) {
        if (amount < MIN_AMOUNT || MAX_AMOUNT < amount) {
            throw new IllegalArgumentException(ERROR_MESSAGE_OUT_OT_RANGE);
        }
    }

    public int getAmount() {
        return amount;
    }
}
